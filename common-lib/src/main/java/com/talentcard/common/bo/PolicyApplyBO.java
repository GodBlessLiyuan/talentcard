package com.talentcard.common.bo;

import com.talentcard.common.pojo.AnnexPO;
import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.pojo.PolicyApprovalPO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 19:24
 * @description: 政策审批-详情
 * @version: 1.0
 */
@Data
public class PolicyApplyBO extends PolicyApplyPO {
    private String num;
    private String bankNum;
    private String bankName;
    private String roleName;
    private String openId;
    private double initialFunds;
    private List<AnnexPO> annexes;
    private List<PolicyApprovalPO> approval;
}
