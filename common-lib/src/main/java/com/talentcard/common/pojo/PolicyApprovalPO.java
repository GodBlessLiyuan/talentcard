package com.talentcard.common.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_policy_approval
 * @author 
 */
@Data
public class PolicyApprovalPO implements Serializable {
    private Long approvalId;

    private Long paId;

    private Date createTime;

    /**
     * 1：提交；2：审批
     */
    private Byte type;

    private Long userId;

    private String username;

    private Date updateTime;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private BigDecimal actualFunds;

    private static final long serialVersionUID = 1L;
}