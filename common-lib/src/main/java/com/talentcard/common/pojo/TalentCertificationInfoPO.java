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

    private Long talentId;

    private String education;

    private String school;

    /**
     * 1双一流；2海外人才；3啥也不是
     */
    private Byte firstClass;

    private String major;

    private String ptCategory;

    private String pqCategory;

    private String talentCategory;

    private String honourId;

    private String graduateTime;

    private static final long serialVersionUID = 1L;
}