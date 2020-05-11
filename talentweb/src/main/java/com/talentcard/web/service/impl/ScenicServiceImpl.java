package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.ScenicPictureMapper;
import com.talentcard.common.pojo.ScenicEnjoyPO;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.ScenicPicturePO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.ScenicDTO;
import com.talentcard.web.service.IScenicService;
import com.talentcard.web.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private ScenicPictureMapper scenicPictureMapper;

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
            scenicMapper.insert(scenicPO);

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

        ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(dto.getScenicId());
        if (null == scenicPO) {
            return new ResultVO(1102);
        }

        if (null != existPO && !dto.getScenicId().equals(existPO.getScenicId())) {
            return new ResultVO(1101);
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

        // 编辑
        return new ResultVO(1000);
    }

    @Override
    public ResultVO status(Long scenicId, Long status) {
        return null;
    }

    @Override
    public ResultVO detail(Long scenicId) {
        return null;
    }
}
