package com.talentcard.common.bo;

import com.talentcard.common.pojo.StaffPO;
import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:55
 * @description
 */
@Data
public class StaffBO extends StaffPO {
    private Long staffId;
    private String openId;
    private String staffName;
    private Long activityFirstContent;
    private Long activitySecondContent;
    private String activitySecondContentName;
    private Byte sex;
    private String idCard;
    private String phone;
    private Date staffCreateTime;
}
