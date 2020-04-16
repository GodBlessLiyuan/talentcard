package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_cert_approval
 * @author 
 */
@Data
public class CertApprovalPO implements Serializable {
    private Long approvalId;

    private Long certId;

    private Date createTime;

    /**
     * 1：提交；2：审批
     */
    private Byte type;

    private Long cardId;

    private String category;

    private Long userId;

    private Date updateTime;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private static final long serialVersionUID = 1L;
}