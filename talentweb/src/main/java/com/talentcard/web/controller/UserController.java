package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:46 2020/4/10
 * @version: 1.0.0
 * @description:用户
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    IUserService userService;

    @RequestMapping("updatePassword")
    public ResultVO editPassword(HttpSession session, @RequestParam(value = "oldPassword", required = false) String oldPassword,
                                 @RequestParam(value = "newPassword", required = false) String newPassword) {
        return userService.editPassword(session,oldPassword,newPassword);
    }


}
