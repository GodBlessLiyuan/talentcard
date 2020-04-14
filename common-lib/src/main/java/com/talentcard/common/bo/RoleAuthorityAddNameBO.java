package com.talentcard.common.bo;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:04 2020/4/14
 * @version: 1.0.0
 * @description: 角色权限关联表和权限表join
 */
@Data
public class RoleAuthorityAddNameBO {
    private Long raId;
    private byte status;
    private Long authorityId;
    private Long roleId;
    private String authorityName;

}
