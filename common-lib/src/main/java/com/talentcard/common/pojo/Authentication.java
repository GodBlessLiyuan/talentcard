package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card_authentication
 * @author 
 */
@Data
public class Authentication implements Serializable {
    /**
     * 认证表id
     */
    private Long authId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 政治面貌
     */
    private Byte politicalStatus;

    /**
     * 学历
     */
    private Byte educationBackground;

    /**
     * 学校
     */
    private String school;

    /**
     * 是否为双一流
     */
    private Byte fasmouSchool;

    /**
     * 专业
     */
    private String major;

    /**
     * 学历证明扫描件
     */
    private String educationCertification;

    /**
     * 职称类别
     */
    private String profssionalTitle;

    /**
     * 职称信息
     */
    private String profssionalTitleInfo;

    /**
     * 职业资格
     */
    private String professionalQaulification;

    /**
     * 职业资格信息
     */
    private String professionalQaulificationInfo;

    /**
     * 状态 : 人才卡第一次申请状态
审批中
使用中
已废弃
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date upadateTime;

    /**
     * 是否删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}