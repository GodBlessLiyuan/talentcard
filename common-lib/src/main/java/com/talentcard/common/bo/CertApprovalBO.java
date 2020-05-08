package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:42 2020/4/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class CertApprovalBO {
    private Long approvalId;

    private Long certId;

    private Date createTime;

    /**
     * 1：提交；2：审批
     */
    private Byte type;

    private Long cardId;

    private String category;

    /**
     * 审批人的姓名
     */
    private String name;

    private Date updateTime;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private String cardName;
}
