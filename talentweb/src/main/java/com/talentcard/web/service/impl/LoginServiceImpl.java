package com.talentcard.web.service.impl;

import com.talentcard.common.bo.RoleAuthorityAddNameBO;
import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.mapper.AuthorityMapper;
import com.talentcard.common.mapper.RoleAuthorityMapper;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ILoginService;
import com.talentcard.web.utils.Md5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

import static com.talentcard.web.controller.LoginController.VERIFY_ID;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:42 2020/4/9
 * @version: 1.0.0
 * @description:
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Resource
    UserMapper userMapper;
    @Resource
    RoleAuthorityMapper roleAuthorityMapper;

    public static final String SALT = "talentCard";

    @Override
    public ResultVO login(HttpSession session, HttpServletResponse response, String username, String password, String checkCode) {
        // 1.首先先根据唯一用户名查询当前用户的信息，得到userId
        UserPO userPo = userMapper.queryByName(username);
        //System.out.println(userPo.getUserId());
        // 2. 若不为空，则校验密码是否正确，若不对，提示无此用户名
        if (null!=userPo) {
            // 查询到的编码是加密过的
            String realPassword = userPo.getPassword();
            try {
                password = Md5Util.encodeByMd5(SALT + password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!password.equals(realPassword)) {
                //密码错误
                return new ResultVO(2101);
            }
        } else { //用户名错误
            return new ResultVO(2100);

        }
        //3 .校验验证码，先从session中提取出验证码信息，然后对比用户输入
//        String code = (String)session.getAttribute(VERIFY_ID);
        String code = "1234";
        if (null == code) {
            // 验证码过期
            return new ResultVO(2102);
        }
        if (!code.equalsIgnoreCase(checkCode)) {
            //验证码错误
            return new ResultVO(2103);
        }
        // 4 . 将用户信息存放到session当中
        session.setAttribute("userId",userPo.getUserId());
        session.setAttribute("username",userPo.getUsername());
        System.out.println("这里的session"+session);
        // 5 . 获取当前用户权限，决定展示内容区别
        List<RoleAuthorityAddNameBO> bos = roleAuthorityMapper.queryByRoleIdName(userPo.getRoleId());
        RoleAuthorityBO roleAuthorityBO = RoleAuthorityBO.convert(bos);
        session.setAttribute("userAuthority", roleAuthorityBO);
        session.setMaxInactiveInterval(60 * 60 * 2);
        return new ResultVO(1000,username);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @Override
    public ResultVO quit(HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        //退出登入成功
        return new ResultVO(1000);
    }
}
