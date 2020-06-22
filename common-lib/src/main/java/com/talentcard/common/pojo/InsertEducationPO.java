package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_insert_education
 * @author 
 */
@Data
public class InsertEducationPO implements Serializable {
    private Long insertEducId;

    private Integer education;

    private String school;

    /**
     * 1：是；2：否
     */
    private Byte firstClass;

    private String major;

    private String educPicture;

    private String openId;

    private Byte status;

    private String graduateTime;

    private Long insertCertId;

    private static final long serialVersionUID = 1L;
}