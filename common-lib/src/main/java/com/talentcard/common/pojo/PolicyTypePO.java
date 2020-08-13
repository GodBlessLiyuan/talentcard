package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_policy_type
 * @author 
 */
@Data
public class PolicyTypePO implements Serializable {
    private Long pTid;

    /**
     * 政策类型名称
     */
    private String pTypeName;

    /**
     * 政策类型互斥Id
     */
    private String excludeId;

    /**
     * 政策类型适配最好的政策Id
     */
    private String bestPolicys;

    /**
     * 1：上架；2：下架
     */
    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    /**
     * 描述
     */
    private String description;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}