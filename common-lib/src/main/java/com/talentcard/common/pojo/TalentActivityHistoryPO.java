package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_activity_history
 * @author 
 */
@Data
public class TalentActivityHistoryPO implements Serializable {
    private Long tahId;

    private String openId;

    /**
     * 1 旅游
2 农家乐
     */
    private Long activityFirstContentId;

    private Long activitySecondContentId;

    private String activitySecondContentName;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}