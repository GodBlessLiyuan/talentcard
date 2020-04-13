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
     * 1 刚注册 2：已同意使用中；3：待审批；4：已驳回 5废弃
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}