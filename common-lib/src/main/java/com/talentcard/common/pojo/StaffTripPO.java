package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_staff_trip
 * @author 
 */
@Data
public class StaffTripPO implements Serializable {
    private Long stId;

    private Long scenicId;

    private Long staffId;

    private Date createTime;

    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}