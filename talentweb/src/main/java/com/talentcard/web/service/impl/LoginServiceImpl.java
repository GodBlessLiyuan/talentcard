package com.talentcard.web.service.impl;

import com.talentcard.common.bo.RoleAuthortyNameBO;
import com.talentcard.common.mapper.AuthorityMapper;
import com.talentcard.common.mapper.RoleAuthorityMapper;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.RoleAuthorityPO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ILoginService;
import com.talentcard.web.vo.RoleAuthorityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Resource
    AuthorityMapper authorityMapper;

    public static final String VERIFY_ID = "verifyKeyLogo";

    @Override
    public ResultVO login(HttpSession session, HttpServletResponse response, String username, String password, String checkCode) {

//         1 .校验验证码，先从session中提取出验证码信息，然后对比用户输入
//        String code = (String)session.getAttribute(VERIFY_ID);
        String code = "1234";
        if (null == code) {
            return new ResultVO(1018);
        }
        if (!code.equalsIgnoreCase(checkCode)) {
            return new ResultVO(1019);
        }

        // 2.首先先根据唯一用户名查询当前用户的信息，得到userId
        UserPO userPo = userMapper.queryByName(username);
//        System.out.println(userPo.getUserId());
        // 3. 若不为空，则校验密码是否正确，若不对，提示无此用户名
        if (null!=userPo) {
            String realPassword = userPo.getPassword();
            if (!password.equals(realPassword)) {
                return new ResultVO(1020);
            }
        } else {
            return new ResultVO(1021);
        }
        // 4 . 将用户信息存放到session当中
        session.setAttribute("userId",userPo.getUserId());
        session.setAttribute("username",userPo.getUsername());
        // 5 . 获取当前用户权限，决定展示内容区别
        List<RoleAuthortyNameBO> roleAuthortyNameBOS = new ArrayList<>();
        List<RoleAuthorityPO> roleAuthorityPOS = roleAuthorityMapper.queryByRoleId(userPo.getRoleId());
        for (RoleAuthorityPO po:roleAuthorityPOS) {
            RoleAuthortyNameBO roleAuthortyNameBO = new RoleAuthortyNameBO();
            Long authorityId = po.getAuthorityId();
            String authorityName = authorityMapper.queryByAuthorityId(authorityId);
            roleAuthortyNameBO.setAuthorityName(authorityName);
            roleAuthortyNameBO.setRoleAuthorityPO(po);
        }
        RoleAuthorityVO roleAuthorityVO = RoleAuthorityVO.convert(roleAuthortyNameBOS);
        session.setAttribute("userAuthority", roleAuthorityVO);
        session.setMaxInactiveInterval(60 * 60 * 2);

        return new ResultVO(1000);
    }
}
