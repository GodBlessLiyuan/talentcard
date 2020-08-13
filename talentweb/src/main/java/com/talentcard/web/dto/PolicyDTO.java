package com.talentcard.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:38
 * @description: 政策
 * @version: 1.0
 */
@Data
public class PolicyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策权益ID
     */
    private Long pid;
    /**
     * 政策权益名称
     */
    private String name;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 政策详细描述
     */
    private String desc;
    /**
     * 可查看与申请此政策权益的人才卡IDs
     */
    private String[] cardIds;
    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private String[] categoryIds;
    /**
     * 可查看与申请此政策权益的人才学历IDs
     */
    private String[] educIds;
    /**
     * 可查看与申请此政策权益的人才职称IDs
     */
    private String[] titleIds;
    /**
     * 可查看与申请此政策权益的人才职业资格IDs
     */
    private String[] qualityIds;
    /**
     * 可查看与申请此政策权益的人才荣誉IDs
     */
    private Long[] talentHonourIds;
    /**
     * 颜色，当apply是1的时候，这里必须给
     */
    private String color;
    /**
     * 银行卡信息；1：需要；2：不需要
     */
    private Byte bank;
    /**
     * 附件信息；1：必填；2：不填；3：选填
     */
    private Byte annex;
    /**
     * 附件信息描述
     */
    private String info;
    /**
     * 申请表单
     */
    private String form;
    /**
     * 政策资金
     */
    private Integer funds;
    /**
     * 政策类型
     */
    private Long policyType;
    /**
     * 责任单位/角色id
     */
    private Long roleId;
    /**
     * 是否社保要求
     */
    private Byte ifSocialSecurity;
    /**
     * 地区
     */
    private Byte socialArea;
    /**
     * 社保时间
     */
    private String socialTimes;
    /**
     * 	社保频次
     */
    private Byte socialUnit;
    /**
     * 资金发放形式
     */
    private Byte fundsForm;
    /**
     * 申报对象
     */
    private String declarationTarget;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 申请资料
     */
    private String applyMaterials;
    /**
     * 奖励/补助额度
     */
    private String bonus;
    /**
     * 办事流程
     */
    private String businessProcess;
    /**
     * 咨询电话
     */
    private String phone;
}
