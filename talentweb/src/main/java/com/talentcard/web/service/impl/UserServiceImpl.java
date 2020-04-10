package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:50 2020/4/10
 * @version: 1.0.0
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public ResultVO editPassword(HttpSession session, String oldPassword,String newPassword){
//        long userId = (Long) session.getAttribute("userId");
        long userId = 1;
        UserPO userPO = userMapper.selectByPrimaryKey(userId);

        if (null == userPO) {
            // 用户过期
            return new ResultVO(1024);
        }
        if (!oldPassword.equals(userPO.getPassword())) {
            // 旧密码输入错误
            return new ResultVO(1025);
        }else {
            userMapper.updatePassword(newPassword,userId);
        }
        return new ResultVO(1000);
    }

}
