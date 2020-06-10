package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_farmhouse
 * @author 
 */
@Data
public class FarmhousePO implements Serializable {
    private Long farmhouseId;

    private String name;

    private Double discount;

    private String avatar;

    private String description;

    private String extra;

    private String qrCode;

    /**
     * 1：上架；2：下架
     */
    private Byte status;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String subtitle;

    private Integer area;

    private String location;

    private Long averageCost;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}