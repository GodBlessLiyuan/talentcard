package com.talentcard.common.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_policy_apply
 * @author 
 */
@Data
public class PolicyApplyPO implements Serializable {
    private Long paId;

    private Long talentId;

    private String talentName;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private String policyName;

    private Date createTime;

    /**
     * 1：已同意；2：已驳回；3：待审批
     */
    private Byte status;

    private BigDecimal actualFunds;

    private Long policyApprovalId;

    private static final long serialVersionUID = 1L;
}