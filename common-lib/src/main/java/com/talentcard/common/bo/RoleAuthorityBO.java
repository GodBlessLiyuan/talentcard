package com.talentcard.common.bo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:40 2020/4/9
 * @version: 1.0.0
 * @description: 所有权限清单实体类
 */
@Data
@Component
public class RoleAuthorityBO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 系统管理权限, 1关闭，2开放
     * roleQuery 查看角色
     * userCreate 新建用户
     * userUpdate 修改用户
     * userDelete 删除用户
     * userQuery 查看用户
     */
    private Byte roleQuery;
    private Byte userCreate;
    private Byte userUpdate;
    private Byte userDelete;
    private Byte userQuery;

    /**
     * 卡片管理权限，1关闭，2开放
     * userCardUpdate 修改卡片
     * userCardDelete 删除卡片
     * userCardQuery 查看卡片
     * userCardCreate 新建卡片
     */
    private Byte userCardUpdate;
    private Byte userCardDelete;
    private Byte userCardQuery;
    private Byte userCardCreate;

    /**
     * 人才管理权限，1关闭，2开放
     * generalTalentQuery 普通用户查看
     * verifiedTalentQuery 认证用户查看
     * approvalTalentQuery 认证审批查看
     * approvalTalentAction 认证审批审批
     */
    private Byte generalTalentQuery;
    private Byte verifiedTalentQuery;
    private Byte approvalTalentQuery;
    private Byte approvalTalentAction;

    /**
     * 人才政策管理权限，1关闭，2开放
     * policyCreate 新建政策
     * policyUpdate 修改政策
     * policyDelete 删除政策
     * policyQuery 查看政策
     * approvalPolicyQuery 政策审批查看
     * approvalPolicyAction 政策审批审批
     */
    private Byte policyCreate;
    private Byte policyUpdate;
    private Byte policyDelete;
    private Byte policyQuery;
    private Byte approvalPolicyQuery;
    private Byte approvalPolicyAction;

    /**
     * 将角色权限表实体类转换为其VO表示实体类
     * @param bos
     * @return
     */
    public static RoleAuthorityBO convert(List<RoleAuthorityAddNameBO> bos) {
        RoleAuthorityBO vo = new RoleAuthorityBO();
        for (RoleAuthorityAddNameBO bo : bos) {
            String RoleAuthortyName = bo.getAuthorityName();
            switch (RoleAuthortyName){
                case "roleQuery": vo.setRoleQuery(bo.getStatus());break;
                case "userCreate": vo.setUserCreate(bo.getStatus());break;
                case "userUpdate": vo.setUserUpdate(bo.getStatus());break;
                case "userDelete": vo.setUserDelete(bo.getStatus());break;
                case "userQuery": vo.setUserQuery(bo.getStatus());break;
                case "userCardUpdate": vo.setUserCardUpdate(bo.getStatus());break;
                case "userCardDelete": vo.setUserCardDelete(bo.getStatus());break;
                case "userCardQuery": vo.setUserCardQuery(bo.getStatus());break;
                case "userCardCreate": vo.setUserCardCreate(bo.getStatus());break;
                case "generalTalentQuery": vo.setGeneralTalentQuery(bo.getStatus());break;
                case "verifiedTalentQuery": vo.setVerifiedTalentQuery(bo.getStatus());break;
                case "approvalTalentQuery": vo.setApprovalTalentQuery(bo.getStatus());break;
                case "approvalTalentAction": vo.setApprovalTalentAction(bo.getStatus());break;
                case "policyCreate": vo.setPolicyCreate(bo.getStatus());break;
                case "policyUpdate": vo.setPolicyUpdate(bo.getStatus());break;
                case "policyDelete": vo.setPolicyDelete(bo.getStatus());break;
                case "policyQuery": vo.setPolicyQuery(bo.getStatus());break;
                case "approvalPolicyQuery": vo.setApprovalPolicyQuery(bo.getStatus());break;
                case "approvalPolicyAction": vo.setApprovalPolicyAction(bo.getStatus());break;
            }
        }
        return vo;
    }


    @Override
    public String toString() {
        return "RoleAuthorityBO{" +
                "roleQuery=" + roleQuery +
                ", userCreate=" + userCreate +
                ", userUpdate=" + userUpdate +
                ", userDelete=" + userDelete +
                ", userQuery=" + userQuery +
                ", userCardUpdate=" + userCardUpdate +
                ", userCardDelete=" + userCardDelete +
                ", userCardQuery=" + userCardQuery +
                ", userCardCreate=" + userCardCreate +
                ", generalTalentQuery=" + generalTalentQuery +
                ", verifiedTalentQuery=" + verifiedTalentQuery +
                ", approvalTalentQuery=" + approvalTalentQuery +
                ", approvalTalentAction=" + approvalTalentAction +
                ", policyCreate=" + policyCreate +
                ", policyUpdate=" + policyUpdate +
                ", policyDelete=" + policyDelete +
                ", policyQuery=" + policyQuery +
                ", approvalPolicyQuery=" + approvalPolicyQuery +
                ", approvalPolicyAction=" + approvalPolicyAction +
                '}';
    }
}
