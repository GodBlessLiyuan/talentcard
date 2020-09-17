package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * po_compliance
 * @author 
 */
@Data
public class PoCompliancePO implements Serializable {
    /**
     * 复合人才政策的自增id
     */
    private Long pCoId;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private Long talentId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 11：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请
     */
    private Byte status;
    /**
     * 申请年份
     */
    private int year;

    private static final long serialVersionUID = 1L;
}