package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:14
 * @description
 */
@Data
public class StaffTripBO{
    private Long staffId;
    private String openId;
    private String staffName;
    private Long activityFirstContent;
    private Long activitySecondContent;
    private Byte sex;
    private String idCard;
    private String phone;
    private Date staffCreateTime;

    private Long scenicId;
    private String scenicName;
    private Integer rate;
    private Byte unit;
    private Integer times;
    private String avatar;
    private String description;
    private String extra;
    private String qrCode;
    private Byte scenicStatus;
    private Date scenicCreateTime;

    private Long stId;
    private Date stCreateTime;
    private Byte stStatus;
}
