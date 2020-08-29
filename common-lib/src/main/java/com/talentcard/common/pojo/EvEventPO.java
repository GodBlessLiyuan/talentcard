package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ev_event
 *
 * @author
 */
@Data
public class EvEventPO implements Serializable {
    private Long eventId;

    /**
     * 1上架；2下架
     */
    private Byte upDown;

    private String name;

    private String num;

    private String sponsor;

    private String time;

    private Double duration;

    /**
     * 活动场地id
     */
    private Long efId;

    private String date;

    private String detail;

    /**
     * 将一天时间划分成48个小时间段
     */
    private String timeInterval;

    private String process;

    private String target;

    private String contact;

    private String picture;

    private Long roleId;

    private Byte ifQuota;

    private Integer maleQuota;

    private Integer femaleQuota;

    private Integer eventQuota;

    private Date updateTime;

    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private Date createTime;

    private Long userId;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;

}