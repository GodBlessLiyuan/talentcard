package com.talentcard.web.vo;

import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.bo.RoleAuthortyNameBO;
import com.talentcard.common.bo.UserRoleBO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:16 2020/4/13
 * @version: 1.0.0
 * @description: 用以展示当前查询用户的权限列表，在操作行列下展示系统权限
 */
@Data
public class UserRoleVO {

    private String username;

    private String name;

    private String roleName;

    private String extra;

    /**
     * 用户权限name in ('userUpdate','userDelete') 特定查询编辑权限和删除权限
     * 用status状态为1，进行权限名拼接，间隔符->逗号
     */
    private String authorityName;


    public static List<UserRoleVO> convert(List<UserRoleBO> userRoleBOS) {
        List<UserRoleVO> userRoleVOS = new ArrayList<>();
        for (UserRoleBO bo : userRoleBOS) {
            UserRoleVO vo = new UserRoleVO();
            vo.setAuthorityName(bo.getAuthorityName());
            vo.setExtra(bo.getExtra());
            vo.setName(bo.getName());
            vo.setRoleName(bo.getRoleName());
            vo.setUsername(bo.getUsername());
            userRoleVOS.add(vo);
        }
        return userRoleVOS;
    }


}
