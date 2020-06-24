package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_insert_cert_approval
 * @author 
 */
@Data
public class InsertCertApprovalPO implements Serializable {
    private Long icaId;

    private Date createTime;

    /**
     * 1：提交；2：审批
     */
    private Byte type;

    private Long userId;

    private Date updateTime;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private Long insertCertId;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}