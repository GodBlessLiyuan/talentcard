package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-13 09:48
 * @description
 */
@Data
public class ActivityHistoryBO {
    private Long activityFirstContentId;
    private Long activitySecondContentId;
    private String activitySecondContentName;
    private Date createTime;
}
