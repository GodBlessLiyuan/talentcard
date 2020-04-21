package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:10 2020/4/13
 * @version: 1.0.0
 * @description:
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Resource
    IRoleService roleService;

    /**
     * 根据角色名和角色创建时间的开始结束时间间隔进行角色用户的查询
     * @param roleName
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("queryByRole")
    public ResultVO queryByRole(@RequestParam(value = "roleName", required = false) String roleName,
                                @RequestParam(value = "startTime", required = false) String startTime,
                                @RequestParam(value = "endTime", required = false) String endTime) {
        return roleService.queryByRole(roleName,startTime,endTime);
    }


    @RequestMapping("queryRoleIdMsg")
    public ResultVO queryRoleIdMsg() {
        return roleService.queryRoleNameIdMsg();
    }
}
