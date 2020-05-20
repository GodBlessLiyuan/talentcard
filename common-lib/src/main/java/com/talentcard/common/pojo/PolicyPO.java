package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_policy
 * @author 
 */
@Data
public class PolicyPO implements Serializable {
    private Long policyId;

    private String name;

    private String num;

    private String cards;

    private String categories;

    private String educations;

    private String titles;

    private String qualities;

    /**
     * 可申请人才荣誉IDs
     */
    private String talentHonour;

    /**
     * 1：需要；2：不需要
     */
    private Byte apply;

    private Integer rate;

    private Byte unit;

    private Integer times;

    /**
     * 1：需要；2：不需要；
     */
    private Byte bank;

    /**
     * 1：需要；2：不需要；
     */
    private Byte annex;

    private Long userId;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String description;

    private static final long serialVersionUID = 1L;
}