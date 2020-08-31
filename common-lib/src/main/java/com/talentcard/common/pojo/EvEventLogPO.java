package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_event_log
 * @author 
 */
@Data
public class EvEventLogPO implements Serializable {
    private Long elId;

    private Date createTime;

    private Long userId;

    /**
     * 1发起活动；
2取消活动；
3上架；
4下架
     */
    private Byte type;

    /**
     * 1：同意；2：拒绝
     */
    private Byte result;

    private String opinion;

    private Long eventId;

    private static final long serialVersionUID = 1L;
}