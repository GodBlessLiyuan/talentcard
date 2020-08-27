package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * ev_event_enjoy
 * @author 
 */
@Data
public class EvEventEnjoyPO implements Serializable {
    private Long eeId;

    private Long eventId;

    private Long cardId;

    /**
     * 人才类别ID
     */
    private Long category;

    /**
     * 学历ID
     */
    private Integer education;

    /**
     * 职称ID
     */
    private Integer title;

    /**
     * 人才职业资格ID
     */
    private Integer quality;

    private Long honour;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}