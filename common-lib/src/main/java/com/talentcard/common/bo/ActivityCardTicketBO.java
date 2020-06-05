package com.talentcard.common.bo;

import lombok.Data;
/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-05 08:43
 * @description
 */
@Data
public class ActivityCardTicketBO {
    private Long activityFirstContentId;
    private Long activitySecondContentId;
    private String activitySecondContentName;
    private String usagePeriod;
}
