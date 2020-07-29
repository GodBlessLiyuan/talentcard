package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.FarmhouseEnjoyMapper;
import com.talentcard.common.mapper.FarmhouseGroupAuthorityMapper;
import com.talentcard.common.mapper.FarmhouseMapper;
import com.talentcard.common.mapper.FarmhousePictureMapper;
import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.QrCodeUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.FarmhouseDTO;
import com.talentcard.web.service.IFarmhouseService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.vo.FarmhouseDetailVO;
import com.talentcard.web.vo.FarmhouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 18:42
 * @description: 农家乐
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class FarmhouseServiceImpl implements IFarmhouseService {
    @Autowired
    private FarmhouseMapper farmhouseMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private FarmhousePictureMapper farmhousePictureMapper;
    @Autowired
    private FarmhouseGroupAuthorityMapper farmhouseGroupAuthorityMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private ILogService logService;
    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<FarmhousePO> page = PageHelper.startPage(pageNum, pageSize);
        List<FarmhousePO> pos = farmhouseMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), FarmhouseVO.convert(pos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(HttpSession session,FarmhouseDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        FarmhousePO existPO = farmhouseMapper.queryByName(dto.getName());
        if (null == dto.getFarmhouseId()) {
            // 新建
            if (null != existPO) {
                return new ResultVO(1101);
            }

            FarmhousePO farmhousePO = FarmhouseDTO.buildPO(new FarmhousePO(), dto);
            farmhousePO.setCreateTime(new Date());
            farmhousePO.setStatus((byte) 2);
            farmhousePO.setDr((byte) 1);
            farmhouseMapper.insert(farmhousePO);

            String qrCode = null;
            try {
                String url = filePathConfig.getPublicBasePath() + "/wx/#/jump?type=2&id=" + farmhousePO.getFarmhouseId() + "&name=" + farmhousePO.getName();
                qrCode = QrCodeUtil.encode(url, null, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getQrCodeDir(), null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultVO(2000);
            }
            farmhousePO.setQrCode(qrCode);
            farmhouseMapper.updateByPrimaryKey(farmhousePO);

            List<FarmhouseEnjoyPO> enjoyPOs = FarmhouseDTO.buildEnjoyPOs(dto, farmhousePO.getFarmhouseId());
            if (enjoyPOs.size() > 0) {
                farmhouseEnjoyMapper.batchInsert(enjoyPOs);
            }

            List<FarmhousePicturePO> picPOs = FarmhouseDTO.buildPicturePOs(dto, farmhousePO.getFarmhouseId());
            if (picPOs.size() > 0) {
                farmhousePictureMapper.batchInsert(picPOs);
            }
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FarmHouse,
                    "新建农家乐\"%s\"",farmhousePO.getName());
            return new ResultVO(1000);
        }

        // 编辑
        FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(dto.getFarmhouseId());
        if (null == farmhousePO) {
            return new ResultVO(1102);
        }

        if (null != existPO && !dto.getFarmhouseId().equals(existPO.getFarmhouseId())) {
            return new ResultVO(1101);
        }

        if (!dto.getName().equals(farmhousePO.getName())) {
            try {
                String url = filePathConfig.getPublicBasePath() + "/wx/#/jump?type=2&id=" + dto.getFarmhouseId() + "&name=" + dto.getName();
                QrCodeUtil.encode(url, null, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getQrCodeDir(), farmhousePO.getQrCode(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FarmhouseDTO.buildPO(farmhousePO, dto);
        farmhouseMapper.updateByPrimaryKey(farmhousePO);

        farmhouseEnjoyMapper.deleteByFarmhouseId(dto.getFarmhouseId());
        List<FarmhouseEnjoyPO> enjoyPOs = FarmhouseDTO.buildEnjoyPOs(dto, farmhousePO.getFarmhouseId());
        if (enjoyPOs.size() > 0) {
            farmhouseEnjoyMapper.batchInsert(enjoyPOs);
        }

        farmhousePictureMapper.deleteByFarmhouseId(dto.getFarmhouseId());
        List<FarmhousePicturePO> picPOs = FarmhouseDTO.buildPicturePOs(dto, farmhousePO.getFarmhouseId());
        if (picPOs.size() > 0) {
            farmhousePictureMapper.batchInsert(picPOs);
        }

        farmhouseGroupAuthorityMapper.clear();
        deleteRedisCache();
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FarmHouse,
                "编辑农家乐\"%s\"",farmhousePO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO status(HttpSession session,Long farmhouseId, Byte status) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(farmhouseId);
        if (null == farmhousePO) {
            return new ResultVO(1102);
        }

        if (status == 1) {
            farmhousePO.setUpdateTime(new Date());
        } else {
            farmhousePO.setUpdateTime(null);
        }
        farmhousePO.setStatus(status);
        farmhouseMapper.updateByPrimaryKey(farmhousePO);
//        farmhouseMapper.updateStatus(farmhouseId, status);
        farmhouseGroupAuthorityMapper.clear();
        deleteRedisCache();
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FarmHouse,
                "%s架农家乐\"%s\"",status == 1?"上":"下",farmhousePO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long farmhouseId) {
        FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(farmhouseId);
        if (null == farmhousePO) {
            return new ResultVO(1102);
        }
        List<FarmhouseEnjoyPO> enjoyPOs = farmhouseEnjoyMapper.queryByFarmhouseId(farmhouseId);
        List<FarmhousePicturePO> picPOs = farmhousePictureMapper.queryByFarmhouseId(farmhouseId);

        FarmhouseDetailVO vo = FarmhouseDetailVO.build(farmhousePO, enjoyPOs, picPOs);
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
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getFarmHouseDir(), "farmhouse");
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_FarmHouse,
                "%s上传文件",(String) session.getAttribute("username"));
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }

    private void deleteRedisCache() {
        redisMapUtil.del("talentfarmhouse");
    }
}
