package com.talentcard.wechat.service;

import com.talentcard.common.vo.ResultVO;

public interface ICardActivateService {
    ResultVO activate(String openId);
}
