package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 普通用户更新用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("updatePassword")
    public ResultVO editPassword(HttpServletRequest request, @RequestParam(value = "oldPassword", required = false) String oldPassword,
                                 @RequestParam(value = "newPassword", required = false) String newPassword) {
        return userService.editPassword(request.getSession(), oldPassword, newPassword);
    }

    @RequestMapping("insertUser")
    public ResultVO insertUser(HttpServletRequest request,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "roleId", required = false) Long roleId,
                               @RequestParam(value = "extra", required = false) String extra) {

        return userService.insertUser(request.getSession(), username, password, name, roleId, extra);
    }

    /**
     * 编辑用户，用户名不能修改，其它可改
     *
     * @param username
     * @param name
     * @param roleId
     * @param extra
     * @return
     */
    @RequestMapping("editUser")
    public ResultVO editUser(HttpServletRequest request,
                             @RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "roleId", required = false) Long roleId,
                             @RequestParam(value = "extra", required = false) String extra) {
        return userService.editUser(request.getSession(), username, name, roleId, extra);
    }

    /**
     * 管理员修改密码
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("adminUpUserPassword")
    public ResultVO managerUpdatePassword(HttpServletRequest request,
                                          @RequestParam(value = "username", required = false) String username,
                                          @RequestParam(value = "password", required = false) String password) {
        return userService.adminUpdatePassword(request.getSession(), username, password);
    }

    /**
     * 根据用户名删除用户
     *
     * @param username
     * @return
     */
    @RequestMapping("deleteUser")
    public ResultVO deleteUser(HttpServletRequest request,
                               @RequestParam(value = "username", required = false) String username) {
        return userService.deleteUser(request.getSession(), username);
    }

    /**
     * 根据用户名和角色id获取当前用户系统权限列表，需要分页展示
     *
     * @param username
     * @param roleId
     * @return
     */
    @RequestMapping("queryByUser")
    public ResultVO queryByUser(HttpServletRequest request,
                                @RequestParam(value = "start", defaultValue = "1") int pageNum,
                                @RequestParam(value = "length", defaultValue = "10") int pageSize,
                                @RequestParam(value = "username", defaultValue = "") String username,
                                @RequestParam(value = "roleId", defaultValue = "") Long roleId) {
        Map<String, Object> reqData = new HashMap<>(2);
        username = username.replaceAll("%", "\\\\%");

        reqData.put("username", username.replaceAll(" ", ""));
        reqData.put("roleId", roleId);
        return userService.queryUserRole(pageNum, pageSize, reqData);
    }

    /**
     * 根据session查找相关角色信息
     *
     * @param httpSession
     * @return
     */
    @RequestMapping("findRoleBySession")
    public ResultVO findRoleBySession(HttpSession httpSession) {
        return userService.findRoleBySession(httpSession);
    }
}
