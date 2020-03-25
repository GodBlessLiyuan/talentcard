package com.talentcard.front.service;

import com.talentcard.common.pojo.AuthenticationPO;

public interface IAuthenticationService {
    /**
     * @description 认证审批表的添加功能
     * @param authenticationPO
     * @return
     */
    int insert(AuthenticationPO authenticationPO);
}
