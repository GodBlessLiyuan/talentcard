package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_talent_certification_info
 * @author 
 */
@Data
public class TalentCertificationInfoPO implements Serializable {
    private Long tciId;

    /**
     * 人才Id
     */
    private Long talentId;
    /**
     * 学历
     */
    private String education;

    /**
     * 职称类别
     */
    private String ptCategory;
    /**
     * 职业资格
     */
    private String pqCategory;
    /**
     * 人才类别
     */
    private String talentCategory;
    /**
     * 人才荣誉
     */
    private String honourId;

    private static final long serialVersionUID = 1L;
}