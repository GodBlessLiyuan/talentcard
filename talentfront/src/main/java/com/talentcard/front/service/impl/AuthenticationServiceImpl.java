package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.AuthenticationMapper;
import com.talentcard.common.pojo.AuthenticationAttachmentPO;
import com.talentcard.common.pojo.AuthenticationPO;
import com.talentcard.front.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/25 09:38
 * @description 审批表的Service层
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private AuthenticationMapper authenticationMapper;
    @Override
    public int insert(AuthenticationPO authenticationPO) {
        return authenticationMapper.insertSelective(authenticationPO);
    }
}
