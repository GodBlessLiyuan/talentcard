package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:49 2020/4/10
 * @version: 1.0.0
 * @description:
 */
public interface IUserService {
    /**
     * 根据session得到的id和新密码修改原有密码
     * @param session
     * @param oldPassword
     * @param newPassword
     * @return
     */
    ResultVO editPassword(HttpSession session, String oldPassword,String newPassword);

}
