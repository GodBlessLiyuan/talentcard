package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.mapper.OpwebRecordMapper;
import com.talentcard.common.pojo.OpwebRecordPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IOpwebRecordService;
import com.talentcard.web.vo.OpwebRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OpwebRecordServiceImpl implements IOpwebRecordService {

    @Autowired
    private OpwebRecordMapper opwebRecordMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap) {
        Page<OpwebRecordPO> page = PageHelper.startPage(pageNum, pageSize);
        List<OpwebRecordPO> pos = opwebRecordMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), OpwebRecordVO.convert(pos)));
    }
}
