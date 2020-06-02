package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.talentcard.common.bo.FeedbackBO;
import com.talentcard.common.mapper.FeedbackMapper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFeedbackService;
import com.talentcard.web.vo.FeedBackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 10:46
 * @description: 意见反馈
 * @version: 1.0
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<FeedbackBO> page = PageHelper.startPage(pageNum, pageSize);
        List<FeedbackBO> pos = feedbackMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), FeedBackVO.convert(pos)));
    }
}
