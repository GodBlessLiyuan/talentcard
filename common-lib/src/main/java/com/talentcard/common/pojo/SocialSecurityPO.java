package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ta_social_security
 * @author 
 */
@Data
public class SocialSecurityPO implements Serializable {
    /**
     * 人才ID
     */
    private Long talentId;

    /**
     * 历史工作单位
     */
    private String oldWorkUnit;

    /**
     * 参保单位
     */
    private String securiyWorkUnit;

    /**
     * 本次参保开始时间
     */
    private Long securityTime;

    /**
     * 1：在职人员；2：中断人员；
     */
    private Byte socialType;

    /**
     * 核查时间
     */
    private Date checkTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}