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
    /**
     * 人才政策ID
     */
    private Long policyId;

    private String name;

    private String num;

    private String description;

    private String cards;

    private String categories;

    private String educations;

    private String titles;

    private String qualities;

    private String honourIds;

    /**
     * 1：需要；2：不需要
     */
    private Byte apply;

    private String color;

    private Integer rate;

    private Byte unit;

    private Integer times;

    /**
     * 1：需要；2：不需要；
     */
    private Byte bank;

    /**
     * 1：必填；2：不填；3：选填
     */
    private Byte annex;

    private String annexInfo;

    private String applyForm;

    private Integer funds;

    private Long userId;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private Long pTid;

    private Long roleId;

    /**
     * 1. 一次性发放。2. 按月发放。3. 按年发放。
     */
    private Byte fundsForm;

    private String declarationTarget;

    private Date startTime;

    private Date endTime;

    private String applyMaterials;

    private String bonus;

    private String businessProcess;

    private String phone;

    private Byte ifSocialSecurity;

    private Byte socialArea;

    private Byte socialTimes;

    private Byte socialUnit;

    private Byte upDown;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}