package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:14 2020/10/23
 * @ Description：政策审批查询业务类
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class PolicyApprovalBO {
    /**
     * 政策id
     */
    private Long policyId;
    /**
     * 政策名称
     */
    private String policyName;
    /**
     * 政策编号
     */
    private String num;
    /**
     * 符合条件人数
     */
    private Integer total;
    /**
     * 待审批人数
     */
    private Integer notApprovalNum;
    /**
     * 申请人数
     */
    private Integer notApplyNum;
    /**
     * 已通过人数
     */
    private Integer passNum;
    /**
     * 已拒绝人数
     */
    private Integer rejectNum;
    /**
     * 申报开始时间
     */
    private Date startTime;
    /**
     * 申报结束时间
     */
    private Date endTime;
    /**
     * 政策类型名称
     */
    private String policyTypeName;
    /**
     * 责任单位名称
     */
    private String responsibleUnit;
    /**
     * 政策上下架标识
     */
    private Byte upDown;
}
