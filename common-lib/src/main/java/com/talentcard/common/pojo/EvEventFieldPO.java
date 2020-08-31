package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ev_event_field
 * @author 
 */
@Data
public class EvEventFieldPO implements Serializable {
    private Long efId;

    /**
     * 活动场地
     */
    private String placeName;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}