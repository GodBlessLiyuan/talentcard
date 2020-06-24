package com.talentcard.common.dto;

import lombok.Data;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:33
 * @description
 */
@Data
public class BasicInfoDTO {
    private String openId;
    private String name;
    private Byte sex;
    private String idCard;
    private String passport;
    private String driverCard;
    private Byte cardType;
    private String workUnit;
    private Integer industry;
    private Integer industrySecond;
    private String phone;
    private Byte political;
    private String category;
    private String workLocation;
    private Byte workLocationType;
    private Long cardId;
    private Integer talentSource;
    private String verificationCode;
}
