package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_farmhouse
 * @author 
 */
@Data
public class TalentFarmhousePO implements Serializable {
    private Long ttId;

    private String openId;

    private Long farmhouseId;

    private Long staffId;

    private Double discount;

    private Date effectiveTime;

    private Date updateTime;

    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}