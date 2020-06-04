package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_activity_collect
 * @author 
 */
@Data
public class TalentActivityCollectPO implements Serializable {
    private Long tacId;

    private Long talentId;

    /**
     * 1 旅游
2 农家乐
     */
    private Long activityFirstContentId;

    private Long activitySecondContentId;

    private Date createTime;

    /**
     * 1收藏；2未收藏
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}