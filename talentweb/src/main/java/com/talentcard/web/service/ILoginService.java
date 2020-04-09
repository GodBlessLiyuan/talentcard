package com.talentcard.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:41 2020/4/9
 * @version: 1.0.0
 * @description:
 */
@Service
public interface ILoginService {
    ModelAndView login(HttpSession session, HttpServletResponse response, String phone, String password, String checkCode);
}
