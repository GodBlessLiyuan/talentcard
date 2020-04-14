package com.talentcard.common.bo;

import lombok.Data;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:08 2020/4/13
 * @version: 1.0.0
 * @description:  记录了角色id和对应所有权限的成对属性
 */
@Data
public class RoleAndAuthorityBO {
    private Long roleId;
    private RoleAuthorityBO roleAuthorityBO;

}
