package com.talentcard.web.vo;

import com.talentcard.common.bo.RoleAuthorityBO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:02 2020/4/13
 * @version: 1.0.0
 * @description: 角色权限管理VO
 */
@Data
public class ManageRoleVO {
    private String roleName;
    private Date createTime;
    private String extra;
    private RoleAuthorityBO roleAuthorityBO;
}
