package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.ScenicPictureMapper;
import com.talentcard.common.mapper.TripGroupAuthorityMapper;
import com.talentcard.common.pojo.ScenicEnjoyPO;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.ScenicPicturePO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.QrCodeUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.ScenicDTO;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:04
 * @description: 景区
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class ScenicServiceImpl implements IScenicService {

    private static final Logger logger = LoggerFactory.getLogger(ScenicServiceImpl.class);

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

    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<ScenicPO> page = PageHelper.startPage(pageNum, pageSize);
        List<ScenicPO> pos = scenicMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), ScenicVO.convert(pos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(ScenicDTO dto) {
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
        return new ResultVO(1000);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO status(Long scenicId, Long status) {
        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(scenicId);
        if (null == scenicPO) {
            return new ResultVO(1102);
        }

        scenicMapper.updateStatus(scenicId, status);
        tripGroupAuthorityMapper.clear();
        redisMapUtil.del("talentTrip");
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
            }else if (type == 6) {
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
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getScenicDir(), "scenic");
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }
}
