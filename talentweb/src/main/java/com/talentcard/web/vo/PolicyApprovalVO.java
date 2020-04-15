package com.talentcard.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 15:15
 * @description: 政策审批
 * @version: 1.0
 */
@Data
public class PolicyApprovalVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 时间
     */
    private String ctime;
    /**
     * 类型
     */
    private String type;
    /**
     * 审批人
     */
    private String uname;
    /**
     * 审批结果
     */
    private String result;
    /**
     * 审批意见
     */
    private String opinion;
}
