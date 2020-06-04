package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_trip
 * @author 
 */
@Data
public class TalentTripPO implements Serializable {
    private Long ttId;

    private String openId;

    private Long scenicId;

    private Long staffId;

    private Date createTime;

    private Date effectiveTime;

    private Date updateTime;

    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String usagePeriod;

    private Date effectiveTimeStart;

    private static final long serialVersionUID = 1L;
}