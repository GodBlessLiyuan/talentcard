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

    private String ptCategory;

    private String pqCategory;

    private String talentCategory;

    private String honourId;

    private static final long serialVersionUID = 1L;
}