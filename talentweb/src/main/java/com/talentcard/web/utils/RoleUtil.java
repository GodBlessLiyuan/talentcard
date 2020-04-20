package com.talentcard.web.utils;


import com.talentcard.common.bo.RoleAuthorityAddNameBO;
import com.talentcard.common.bo.RoleAuthorityBO;
import com.talentcard.common.pojo.RoleAuthorityPO;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 8:37 2020/4/14
 * @version: 1.0.0
 * @description: 角色权限的转换
 */
public class RoleUtil {
    /**
     * 将当前角色id的全部权限 拆解 成角色权限关联表
     *
     * @param
     * @return
     */

    public static List<RoleAuthorityAddNameBO> buildRoleAuthorityPOS(List<RoleAuthorityAddNameBO> bos, RoleAuthorityBO bo) {
        for (RoleAuthorityAddNameBO raBo : bos) {
            String authorityName = raBo.getAuthorityName();
            switch (authorityName) {
                case "roleQuery":
                    raBo.setStatus(bo.getRoleQuery());
                    break;
                case "userCreate":
                    raBo.setStatus(bo.getUserCreate());
                    break;
                case "userUpdate":
                    raBo.setStatus(bo.getUserUpdate());
                    break;
                case "userDelete":
                    raBo.setStatus(bo.getUserDelete());
                    break;
                case "userQuery":
                    raBo.setStatus(bo.getUserQuery());
                    break;
                case "userCardUpdate":
                    raBo.setStatus(bo.getUserCardUpdate());
                    break;
                case "userCardDelete":
                    raBo.setStatus(bo.getUserCardDelete());
                    break;
                case "userCardQuery":
                    raBo.setStatus(bo.getUserCardQuery());
                    break;
                case "userCardCreate":
                    raBo.setStatus(bo.getUserCardCreate());
                    break;
                case "generalTalentQuery":
                    raBo.setStatus(bo.getGeneralTalentQuery());
                    break;
                case "verifiedTalentQuery":
                    raBo.setStatus(bo.getVerifiedTalentQuery());
                    break;
                case "verifiedTalentUpdate":
                    raBo.setStatus(bo.getVerifiedTalentUpdate());
                    break;
                case "approvalTalentQuery":
                    raBo.setStatus(bo.getApprovalTalentQuery());
                    break;
                case "approvalTalentAction":
                    raBo.setStatus(bo.getApprovalTalentAction());
                    break;
                case "policyCreate":
                    raBo.setStatus(bo.getPolicyCreate());
                    break;
                case "policyUpdate":
                    raBo.setStatus(bo.getPolicyUpdate());
                    break;
                case "policyDelete":
                    raBo.setStatus(bo.getPolicyDelete());
                    break;
                case "policyQuery":
                    raBo.setStatus(bo.getPolicyQuery());
                    break;
                case "approvalPolicyQuery":
                    raBo.setStatus(bo.getApprovalPolicyQuery());
                    break;
                case "approvalPolicyAction":
                    raBo.setStatus(bo.getApprovalPolicyAction());
                    break;
            }
        }
        return bos;
    }

    /**
     * 将List<RoleAuthorityAddNameBO> 转换为 List<RoleAuthorityPO>
     * @param bos
     * @return
     */
    public static List<RoleAuthorityPO> convert(List<RoleAuthorityAddNameBO> bos) {
        List<RoleAuthorityPO> pos= new ArrayList<>();
        for (RoleAuthorityAddNameBO bo : bos) {
            RoleAuthorityPO po = new RoleAuthorityPO();
            po.setAuthorityId(bo.getAuthorityId());
            po.setRaId(bo.getRaId());
            po.setStatus(bo.getStatus());
            po.setRoleId(bo.getRoleId());
            pos.add(po);
        }
        return pos;
    }


}
