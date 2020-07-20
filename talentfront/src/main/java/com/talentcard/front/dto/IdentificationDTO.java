package com.talentcard.front.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-15 09:41
 * @description
 */
@Data
public class IdentificationDTO {
    private String openId;
    private Integer education;
    private String school;
    private Byte firstClass;
    private String major;
    private Integer profQualityCategory;
    private String profQualityInfo;
    private Integer profTitleCategory;
    private String profTitleInfo;
    private Long honourId;
    private String graduateTime;
    private String educPicture;
    private String educPicture2;
    private String educPicture3;
    private String profTitlePicture;
    private String profTitlePicture2;
    private String profTitlePicture3;
    private String profQualityPicture;
    private String profQualityPicture2;
    private String profQualityPicture3;
    private String talentHonourPicture;
    private String talentHonourPicture2;
    private String talentHonourPicture3;
    private Byte fullTime;
}
