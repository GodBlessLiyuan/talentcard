package com.talentcard.common.bo;

import com.talentcard.common.pojo.TalentActivityHistoryPO;
import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-05 09:23
 * @description
 */
@Data
public class FootprintBO {
    private String openId;
    private Long activityFirstContentId;
    private Long activitySecondContentId;
    private String name;
    private String location;
    private String subtitle;
    private String useTimes;
}
