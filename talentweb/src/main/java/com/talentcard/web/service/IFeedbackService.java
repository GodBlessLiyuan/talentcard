package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 10:46
 * @description: 意见反馈
 * @version: 1.0
 */
public interface IFeedbackService {
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);
}
