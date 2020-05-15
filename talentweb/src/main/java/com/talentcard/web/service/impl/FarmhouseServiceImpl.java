package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.FarmhouseEnjoyMapper;
import com.talentcard.common.mapper.FarmhouseGroupAuthorityMapper;
import com.talentcard.common.mapper.FarmhouseMapper;
import com.talentcard.common.mapper.FarmhousePictureMapper;
import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.QrCodeUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.FarmhouseDTO;
import com.talentcard.web.service.IFarmhouseService;
import com.talentcard.web.vo.FarmhouseDetailVO;
import com.talentcard.web.vo.FarmhouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${file.publicPath}")
    private String publicPath;
    @Value("${file.rootDir}")
    private String rootDir;
    @Value("${file.projectDir}")
    private String projectDir;
    @Value("${file.farmhouseDir}")
    private String farmhouseDir;
    @Value("${file.qrCodeDir}")
    private String qrCodeDir;

    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<FarmhousePO> page = PageHelper.startPage(pageNum, pageSize);
        List<FarmhousePO> pos = farmhouseMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), FarmhouseVO.convert(pos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(FarmhouseDTO dto) {
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
                String url = publicPath + "/wx/#/jump?type=2&id=" + farmhousePO.getFarmhouseId() + "&name=" + farmhousePO.getName();
                qrCode = QrCodeUtil.encode(url, null, rootDir, projectDir, qrCodeDir, true);
            } catch (Exception e) {
                e.printStackTrace();
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

        return new ResultVO(1000);
    }

    @Override
    public ResultVO status(Long farmhouseId, Long status) {
        FarmhousePO farmhousePO = farmhouseMapper.selectByPrimaryKey(farmhouseId);
        if (null == farmhousePO) {
            return new ResultVO(1102);
        }

        farmhouseMapper.updateStatus(farmhouseId, status);
        farmhouseGroupAuthorityMapper.deleteByFarmhouseId(farmhouseId);
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
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, rootDir, projectDir, farmhouseDir, "farmhouse");
        return new ResultVO<>(1000, publicPath + picture);
    }
}
