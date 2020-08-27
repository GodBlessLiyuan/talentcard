package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * ev_frontend_event
 * @author 
 */
@Data
public class EvFrontendEventPO implements Serializable {
    private Long feId;

    private String name;

    private Long eventField;

    private String time;

    private Double duration;

    private String date;

    private String sponsor;

    private String detail;

    private String contactPerson;

    private String phone;

    private String picture;

    /**
     * 人才ID
     */
    private Long talentId;

    private String openId;

    /**
     * 1：已同意；2：已驳回；3：待审批
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}