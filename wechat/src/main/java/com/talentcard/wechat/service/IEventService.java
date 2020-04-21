package com.talentcard.wechat.service;

import com.talentcard.common.vo.ResultVO;

public interface IEventService {
    ResultVO activate(String openId);
    ResultVO delete(String openId);
}
