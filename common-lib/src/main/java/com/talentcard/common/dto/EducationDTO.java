package com.talentcard.common.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:31
 * @description
 */
@Data
public class EducationDTO {
    private Long insertEducId;
    private Integer education;
    private String school;
    private Byte firstClass;
    private String major;
    private String educPicture;
    private String openId;
    private String graduateTime;
    private Long insertCertId;
}
