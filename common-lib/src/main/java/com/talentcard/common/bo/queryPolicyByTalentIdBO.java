package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-19 11:10
 * @description
 */
@Data
public class queryPolicyByTalentIdBO {
    //政策名称
    private String policyName;
    //政策活动开始时间
    private Date startTime;
    //政策活动结束时间
    private Date endTime;
    //状态 0：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请（互斥申请政策存在）
    private Byte status;
}
