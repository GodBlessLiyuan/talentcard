package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.HonourMapper;
import com.talentcard.common.pojo.HonourPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IHonourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
@Service
public class HonourServiceImpl implements IHonourService {
    @Autowired
    HonourMapper honourMapper;
    @Override
    public ResultVO query() {
        List<HonourPO> honourPOList = honourMapper.query(null, null);
        return new ResultVO<>(1000, honourPOList);
    }
}
