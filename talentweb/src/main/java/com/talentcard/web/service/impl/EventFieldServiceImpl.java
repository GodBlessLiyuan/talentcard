package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.EvEventFieldMapper;
import com.talentcard.common.pojo.EvEventFieldPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IEventFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 10:15
 * @description
 */
@Service
public class EventFieldServiceImpl implements IEventFieldService {
    @Autowired
    private EvEventFieldMapper evEventFieldMapper;

    @Override
    public ResultVO queryAll() {
        List<EvEventFieldPO> evEventFieldPOList = evEventFieldMapper.queryAll();
        return new ResultVO(1000, evEventFieldPOList);
    }
}
