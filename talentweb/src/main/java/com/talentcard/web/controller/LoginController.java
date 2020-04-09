package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ILoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:36 2020/4/9
 * @version: 1.0.0
 * @description:
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    ILoginService loginService;
    /**
     * 用户登录验证
     */
    @RequestMapping("checkLogin")
    public ModelAndView login(HttpSession session,
                              HttpServletResponse response,
                              @RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "checkCode",required = false) String checkCode) {
        return loginService.login(session,response,username,password,checkCode);
    }
}
