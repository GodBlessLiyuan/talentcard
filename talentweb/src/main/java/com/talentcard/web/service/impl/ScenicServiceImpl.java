package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.ScenicPictureMapper;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.ScenicDTO;
import com.talentcard.web.service.IScenicService;
import com.talentcard.web.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:04
 * @description: 景区
 * @version: 1.0
 */
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

    @Override
    public ResultVO edit(ScenicDTO dto) {
        ScenicPO existPO = scenicMapper.queryByName(dto.getName());
        if (null == dto.getScenicId()) {
            // 新建
            if(null != existPO) {
                return new ResultVO(1101);
            }

            ScenicPO po = new ScenicPO();
            po.setName(dto.getName());
            po.setRate(dto.getRate());
            po.setUnit(dto.getUnit());
            po.setTimes(dto.getTimes());
        }

        // 编辑
        return null;
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
