package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * op_sendmessage
 * @author 
 */
@Data
public class OpSendmessagePO implements Serializable {
    /**
     * 一键推送id
     */
    private Long sendId;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 成功推送数量
     */
    private Long success;

    /**
     * 失败推送数量
     */
    private Long failure;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

}