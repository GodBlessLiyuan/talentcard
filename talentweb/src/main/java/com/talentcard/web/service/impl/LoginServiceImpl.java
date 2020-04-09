package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.web.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:42 2020/4/9
 * @version: 1.0.0
 * @description:
 */
public class LoginServiceImpl implements ILoginService {
    @Resource
    UserMapper userMapper;

    @Override
    public ModelAndView login(HttpSession session, HttpServletResponse response, String username, String password, String checkCode) {
        ModelAndView mv=new ModelAndView();
        // 1.首先先根据唯一用户名查询当前用户的信息，得到userId
        UserPO userPO = userMapper.queryByName(username);

        // 2. 若不为空，则校验密码是否正确，若不对，提示无此用户名
        if (null!=userPO) {
            String realPassword = userPO.getPassword();
            if (!password.equals(realPassword)) {
                mv.setViewName("login");
                mv.addObject("msg","密码错误");
            }
        } else {
            mv.setViewName("login");
            mv.addObject("msg","用户名错误");
        }
        // 3. 校验验证码的正确性,首先从session当中获得验证码checkcode
        String code = (String) session.getAttribute("checkCode");
        if (null==code) {
            mv.setViewName("login");
        }
        if (!checkCode.equals(code)) {
            mv.setViewName("login");
            mv.addObject("msg","验证码错误");
        }



        // 4 . 将用户信息存放到session当中

        // 5 .
        return mv;
    }
}
