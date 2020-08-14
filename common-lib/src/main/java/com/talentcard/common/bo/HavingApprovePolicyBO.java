package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-14 10:55
 * @description
 */
@Data
public class HavingApprovePolicyBO {
    /**
     * 申请时间
     */
    private Date createTime;
    /**
     * 政策名称
     */
    private String name;
    /**
     * 政策编号
     */
    private String num;
    /**
     * 审批人
     */
    private String userName;
    /**
     * 审批结果
     */
    private Byte Result;
    /**
     * 审批意见
     */
    private String opinion;
    /**
     * 审批时间
     */
    private Date updateTime;
}
