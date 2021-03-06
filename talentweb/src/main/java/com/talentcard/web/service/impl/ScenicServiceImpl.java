package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.pojo.ScenicEnjoyPO;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.ScenicPicturePO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.QrCodeUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.EditTripTimesDTO;
import com.talentcard.web.dto.ScenicDTO;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IScenicService;
import com.talentcard.web.vo.ScenicDetailVO;
import com.talentcard.web.vo.ScenicVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.smartcardio.Card;
import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:04
 * @description: 景区
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class ScenicServiceImpl implements IScenicService {

    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private ScenicPictureMapper scenicPictureMapper;
    @Autowired
    private TripGroupAuthorityMapper tripGroupAuthorityMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private ILogService logService;
    private static final Logger logger=LoggerFactory.getLogger(ScenicServiceImpl.class);
    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<ScenicPO> page = PageHelper.startPage(pageNum, pageSize);
        List<ScenicPO> pos = scenicMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), ScenicVO.convert(pos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(HttpSession session,ScenicDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        dto.setCategoryIds(new Long[]{100L});
        ScenicPO existPO = scenicMapper.queryByName(dto.getName());
        if (null == dto.getScenicId()) {
            // 新建
            if (null != existPO) {
                return new ResultVO(1101);
            }

            ScenicPO scenicPO = ScenicDTO.buildPO(new ScenicPO(), dto);
            scenicPO.setCreateTime(new Date());
            scenicPO.setStatus((byte) 2);
            scenicPO.setDr((byte) 1);
            scenicMapper.insert(scenicPO);

            String qrCode = null;
            try {
                String url = filePathConfig.getPublicBasePath() + "/wx/#/jump?type=1&id=" + scenicPO.getScenicId() + "&name=" + scenicPO.getName();
                qrCode = QrCodeUtil.encode(url, null, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getQrCodeDir(), null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scenicPO.setQrCode(qrCode);
            scenicMapper.updateByPrimaryKey(scenicPO);

            List<ScenicEnjoyPO> enjoyPOs = ScenicDTO.buildEnjoyPOs(dto, scenicPO.getScenicId());
            if (enjoyPOs.size() > 0) {
                scenicEnjoyMapper.batchInsert(enjoyPOs);
            }

            List<ScenicPicturePO> picPOs = ScenicDTO.buildPicturePOs(dto, scenicPO.getScenicId());
            if (picPOs.size() > 0) {
                scenicPictureMapper.batchInsert(picPOs);
            }
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FreeTrip,
                    "新增景区\"%s\"",scenicPO.getName());
            return new ResultVO(1000);
        }

        // 编辑
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(dto.getScenicId());
        if (null == scenicPO) {
            return new ResultVO(1102);
        }

        if (null != existPO && !dto.getScenicId().equals(existPO.getScenicId())) {
            return new ResultVO(1101);
        }

        if (!dto.getName().equals(scenicPO.getName())) {
            try {
                String url = filePathConfig.getPublicBasePath() + "/wx/#/jump?type=1&id=" + dto.getScenicId() + "&name=" + dto.getName();
                QrCodeUtil.encode(url, null, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getQrCodeDir(), scenicPO.getQrCode(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ScenicDTO.buildPO(scenicPO, dto);
        scenicMapper.updateByPrimaryKey(scenicPO);

        scenicEnjoyMapper.deleteByScenicId(dto.getScenicId());
        List<ScenicEnjoyPO> enjoyPOs = ScenicDTO.buildEnjoyPOs(dto, scenicPO.getScenicId());
        if (enjoyPOs.size() > 0) {
            scenicEnjoyMapper.batchInsert(enjoyPOs);
        }

        scenicPictureMapper.deleteByScenicId(dto.getScenicId());
        List<ScenicPicturePO> picPOs = ScenicDTO.buildPicturePOs(dto, scenicPO.getScenicId());
        if (picPOs.size() > 0) {
            scenicPictureMapper.batchInsert(picPOs);
        }

        tripGroupAuthorityMapper.clear();
        redisMapUtil.del("talentTrip");
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FreeTrip,
                "编辑景区\"%s\"",scenicPO.getName());
        return new ResultVO(1000);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO status(HttpSession session,Long scenicId, Byte status) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(scenicId);
        if (null == scenicPO) {
            return new ResultVO(1102);
        }

        if (1 == status) {
            scenicPO.setUpdateTime(new Date());
        } else {
            scenicPO.setUpdateTime(null);
        }
        scenicPO.setStatus(status);
        scenicMapper.updateByPrimaryKey(scenicPO);

//        scenicMapper.updateStatus(scenicId, status);
        tripGroupAuthorityMapper.clear();
        redisMapUtil.del("talentTrip");
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FreeTrip,
                "%s架景区\"%s\"",1 == status?"上":"下",scenicPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long scenicId) {
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(scenicId);
        if (null == scenicPO) {
            return new ResultVO(1102);
        }

        ScenicDetailVO vo = new ScenicDetailVO();
        vo.setScenicId(scenicPO.getScenicId());
        vo.setName(scenicPO.getName());
        vo.setGrade(scenicPO.getStarlevel());
        vo.setIntro(scenicPO.getSubtitle());
        vo.setArea(scenicPO.getArea());
        vo.setLocation(scenicPO.getLocation());
        vo.setRate(scenicPO.getRate());
        vo.setUnit(scenicPO.getUnit());
        vo.setTimes(scenicPO.getTimes());
        vo.setDiscount(scenicPO.getDiscount());
        if (null != scenicPO.getAvatar()) {
            vo.setAvatar(filePathConfig.getPublicBasePath() + scenicPO.getAvatar());
        }
        vo.setDesc(scenicPO.getDescription());
        vo.setExtra(scenicPO.getExtra());
        if (null != scenicPO.getQrCode()) {
            vo.setQrCode(filePathConfig.getPublicBasePath() + scenicPO.getQrCode());
        }

        List<ScenicEnjoyPO> enjoyPOs = scenicEnjoyMapper.queryByScenicId(scenicId);
        List<Long> cardIds = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        List<Integer> educIds = new ArrayList<>();
        List<Integer> titleIds = new ArrayList<>();
        List<Integer> qualityIds = new ArrayList<>();
        List<Long> honourIds = new ArrayList<>();
        for (ScenicEnjoyPO po : enjoyPOs) {
            Byte type = po.getType();
            if (type == 1) {
                cardIds.add(po.getCardId());
            } else if (type == 2) {
                categoryIds.add(po.getCategoryId());
            } else if (type == 3) {
                educIds.add(po.getEducationId());
            } else if (type == 4) {
                titleIds.add(po.getTitleId());
            } else if (type == 5) {
                qualityIds.add(po.getQuality());
            } else if (type == 6) {
                honourIds.add(po.getHonourId());
            }
        }

        vo.setCardIds(cardIds);
        vo.setCategoryIds(categoryIds);
        vo.setEducIds(educIds);
        vo.setTitleIds(titleIds);
        vo.setQualityIds(qualityIds);
        vo.setTalentHonourIds(honourIds);

        List<ScenicPicturePO> picPOs = scenicPictureMapper.queryByScenicId(scenicId);
        List<String> picture = new ArrayList<>();
        for (ScenicPicturePO po : picPOs) {
            picture.add(filePathConfig.getPublicBasePath() + po.getPicture());
        }
        vo.setPicture(picture);

        return new ResultVO<>(1000, vo);
    }

    @Override
    public ResultVO upload(HttpSession session,MultipartFile file) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getScenicDir(), "scenic");
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FreeTrip,
                "\"%s\"上传景区图片",(String) session.getAttribute("username"));
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }

    @Override
    public ResultVO setTripTimes(HttpSession session,EditTripTimesDTO editTripTimesDTO) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        Long[] cardIdArray = editTripTimesDTO.getCardId();
        Integer[] tripTimes = editTripTimesDTO.getTripTimes();
        CardPO cardPO;
        if (cardIdArray == null || tripTimes == null) {
            return new ResultVO(2680);
        }
        if (cardIdArray.length != tripTimes.length) {
            return new ResultVO(2680);
        }
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < cardIdArray.length; i++) {
            Map<String,Object> map=new HashMap<>(2);
            cardPO = null;
            cardPO = cardMapper.selectByPrimaryKey(cardIdArray[i]);
            if (cardPO == null) {
                continue;
            }
            cardPO.setTripTimes(tripTimes[i]);
            cardMapper.updateByPrimaryKeySelective(cardPO);
            builder.append(cardPO.getName()+cardPO.getInitialWord());
            builder.append("/");
            builder.append(tripTimes[i]);
            builder.append("次，");
        }
        String tmp=builder.toString().substring(0,builder.toString().length()-1);
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FreeTrip,
                "设置一批卡次数:%s。",tmp);//卡A/3次，卡A/3次。

        return new ResultVO(1000);
    }
}
