package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import java.util.Map;

public interface IUserFeedbackService {
    ResultVO query(Integer pageNum,Integer pageSize,Map<String, Object> map);
}
