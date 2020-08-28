package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ev_event
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

    private String date;

    private Long eventField;

    private String detail;

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

    private static final long serialVersionUID = 1L;
}