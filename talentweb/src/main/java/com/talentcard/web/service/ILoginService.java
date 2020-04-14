package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:41 2020/4/9
 * @version: 1.0.0
 * @description:
 */
public interface ILoginService {
    /**
     * 用户登入
     * @param session
     * @param response
     * @param phone
     * @param password
     * @param checkCode
     * @return
     */
    ResultVO login(HttpSession session, HttpServletResponse response, String phone, String password, String checkCode);

    /**
     * 用户登出
     * @param request
     * @return
     */
    ResultVO quit(HttpServletRequest request);
}
