package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_event_field
 * @author 
 */
@Data
public class EvEventFieldPO implements Serializable {
    private Long efId;

    /**
     * 场地名称
     */
    private String placeName;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}