package com.talentcard.common.bo;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-19 16:04
 * @description
 */
@Data
public class ApplyNumCountBO {
    //全部数量
    private Integer allNum = 0;
    //待审批数量
    private Integer waitApprovalNum = 0;
    //已通过数量
    private Integer agreeNum = 0;
    //已驳回数量
    private Integer rejectNum = 0;
}
