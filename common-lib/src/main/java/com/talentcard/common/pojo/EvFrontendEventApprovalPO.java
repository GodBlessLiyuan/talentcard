package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_frontend_event_approval
 * @author 
 */
@Data
public class EvFrontendEventApprovalPO implements Serializable {
    private Long approvalId;

    private Long feId;

    /**
     * 审批人ID
     */
    private Long useId;

    /**
     * 审批人
     */
    private String username;

    private Date createTime;

    /**
     * 1：提交；2：审批
     */
    private Byte type;

    private Date updateTime;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private static final long serialVersionUID = 1L;
}