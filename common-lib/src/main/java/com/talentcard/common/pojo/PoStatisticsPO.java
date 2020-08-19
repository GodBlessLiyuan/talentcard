package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * po_statistics
 * @author 
 */
@Data
public class PoStatisticsPO implements Serializable {
    /**
     * 人才政策ID
     */
    private Long policyId;

    /**
     * 符合条件人数
     */
    private Long total;

    /**
     * 符合条件的人数
     */
    private Long notApproval;

    /**
     * 未申请
     */
    private Long notApply;

    /**
     * 已通过
     */
    private Long pass;

    /**
     * 拒绝人数
     */
    private Long reject;

    private static final long serialVersionUID = 1L;


}