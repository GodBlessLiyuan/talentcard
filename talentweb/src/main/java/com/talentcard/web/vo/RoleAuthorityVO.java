package com.talentcard.web.vo;

import com.talentcard.common.bo.RoleAuthortyNameBO;
import com.talentcard.common.pojo.RoleAuthorityPO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:40 2020/4/9
 * @version: 1.0.0
 * @description: 所有权限清单实体类
 */
@Data
@Component
public class RoleAuthorityVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 系统管理权限, 1关闭，2开放
     * roleQuery 查看角色
     * userCreate 新建用户
     * userUpdate 修改用户
     * userDelete 删除用户
     * userQuery 查看用户
     */
    private byte roleQuery;
    private byte userCreate;
    private byte userUpdate;
    private byte userDelete;
    private byte userQuery;

    /**
     * 卡片管理权限，1关闭，2开放
     * userCardUpdate 修改卡片
     * userCardDelete 删除卡片
     * userCardQuery 查看卡片
     * userCardCreate 新建卡片
     */
    private byte userCardUpdate;
    private byte userCardDelete;
    private byte userCardQuery;
    private byte userCardCreate;

    /**
     * 人才管理权限，1关闭，2开放
     * generalTalentQuery 普通用户查看
     * verifiedTalentQuery 认证用户查看
     * approvalTalentQuery 认证审批查看
     * approvalTalentAction 认证审批审批
     */
    private byte generalTalentQuery;
    private byte verifiedTalentQuery;
    private byte approvalTalentQuery;
    private byte approvalTalentAction;

    /**
     * 人才政策管理权限，1关闭，2开放
     * policyCreate 新建政策
     * policyUpdate 修改政策
     * policyDelete 删除政策
     * policyQuery 查看政策
     * approvalPolicyQuery 政策审批查看
     * approvalPolicyAction 政策审批审批
     */
    private byte policyCreate;
    private byte policyUpdate;
    private byte policyDelete;
    private byte policyQuery;
    private byte approvalPolicyQuery;
    private byte approvalPolicyAction;

    /**
     * 将角色权限表实体类转换为其VO表示实体类
     * @param bos
     * @return
     */
    public static RoleAuthorityVO convert(List<RoleAuthortyNameBO> bos) {
        RoleAuthorityVO vo = new RoleAuthorityVO();
        for (RoleAuthortyNameBO bo : bos) {
            String RoleAuthortyName = bo.getAuthorityName();
            switch (RoleAuthortyName){
                case "roleQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userCreate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userUpdate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userDelete": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userCardUpdate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userCardDelete": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userCardQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "userCardCreate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "generalTalentQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "verifiedTalentQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "approvalTalentQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "approvalTalentAction": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "policyCreate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "policyUpdate": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "policyDelete": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "policyQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "approvalPolicyQuery": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
                case "approvalPolicyAction": vo.setRoleQuery(bo.getRoleAuthorityPO().getStatus());break;
            }
        }
        return vo;
    }




}
