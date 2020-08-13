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
     * 0：未申请；1：已同意；2：已驳回；3：待审批
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}