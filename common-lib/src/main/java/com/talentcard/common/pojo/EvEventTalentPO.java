package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_event_talent
 * @author 
 */
@Data
public class EvEventTalentPO implements Serializable {
    private Long etId;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    /**
     * 人才ID
     */
    private Long talentId;

    private String openId;

    private Byte status;

    private Long eventId;

    private static final long serialVersionUID = 1L;
}