package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.front.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/24 18:31
 * @description
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(UserPO userPO) {
        return userMapper.insertReturnId(userPO);
    }
}
