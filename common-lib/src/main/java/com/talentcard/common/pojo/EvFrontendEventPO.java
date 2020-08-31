package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ev_frontend_event
 * @author 
 */
@Data
public class EvFrontendEventPO implements Serializable {
    private Long feId;

    private String name;

    private String time;

    /**
     * 活动场地id
     */
    private Long efId;

    private Double duration;

    /**
     * 活动人数
     */
    private Integer eventNum;

    /**
     * 活动日期
     */
    private Date eventDate;

    /**
     * 将一天时间划分成48个小时间段
     */
    private String timeInterval;

    private String sponsor;

    private String detail;

    private String contactPerson;

    /**
     * 联系方式
     */
    private String phone;

    private String picture;

    /**
     * 人才ID
     */
    private Long talentId;

    private String openId;

    /**
     * 1：提交待审批；2：已同意（已通过）；3：已驳回； 4：管理员取消；5：用户取消
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

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