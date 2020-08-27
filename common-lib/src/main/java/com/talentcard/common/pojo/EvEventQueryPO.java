package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_event_query
 * @author 
 */
@Data
public class EvEventQueryPO implements Serializable {
    private Long eqId;

    private Long eventId;

    private String name;

    private String eventTime;

    private Byte type;

    /**
     * 1 已通过
2 已取消
3 已结束
     */
    private Byte status;

    /**
     * 1上架；2下架
     */
    private Byte upDown;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}