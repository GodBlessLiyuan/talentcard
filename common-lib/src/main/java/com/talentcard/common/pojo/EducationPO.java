package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_education
 * @author 
 */
@Data
public class EducationPO implements Serializable {
    private Long educId;

    private Integer education;

    private String school;

    /**
     * 1：是；2：否
     */
    private Byte firstClass;

    private String major;

    private String educPicture;

    private Long certId;

    private Long talentId;

    /**
     * 1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效
     */
    private Byte status;

    private Byte ifCertificate;

    private static final long serialVersionUID = 1L;
}