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

    private Long efId;

    private Byte type;

    /**
     * 1：提交待审批；2：已同意（已通过）；3：已驳回； 4：管理员取消；5：用户取消
     */
    private Byte status;

    /**
     * 1上架；2下架
     */
    private Byte upDown;

    private Date createTime;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String date;

    private static final long serialVersionUID = 1L;
}