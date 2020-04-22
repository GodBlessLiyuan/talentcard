package com.talentcard.web.service;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.UserRoleVO;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
    ResultVO editPassword(HttpSession session, String oldPassword, String newPassword);

    /**
     * 新建用户
     * @param username
     * @param password
     * @param name
     * @param roleId
     * @param extra
     * @return
     */
    ResultVO insertUser(String username, String password, String name, Long roleId, String extra);

    /**
     * 编辑用户
     * @param username
     * @param name
     * @param roleId
     * @param extra
     * @return
     */
    ResultVO editUser(String username, String name, Long roleId, String extra);

    /**
     * 管理员修改密码
     * @param username
     * @param password
     * @return
     */
    ResultVO adminUpdatePassword(String username, String password);

    /**
     * 根据用户名（用户名不可重复，唯一）删除用户
     * @param username
     * @return
     */
    ResultVO deleteUser(String username);

    /**
     * 根据用户名和角色id获取当前用户系统权限列表，需要分页展示
     * @param pageNum
     * @param pageSize
     * @param reqData
     * @return
     */
    PageInfoVO<UserRoleVO> queryUserRole(int pageNum, int pageSize, Map<String, Object> reqData);

}
