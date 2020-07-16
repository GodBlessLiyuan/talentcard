package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_scenic
 * @author 
 */
@Data
public class ScenicPO implements Serializable {
    private Long scenicId;

    private String name;

    private Integer rate;

    /**
     * 1：年；2：季；3：月
     */
    private Byte unit;

    private Integer times;

    private String avatar;

    private String description;

    private String extra;

    private String qrCode;

    /**
     * 1：上架；2：下架
     */
    private Byte status;

    private Date createTime;

    private Date updateTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String subtitle;

    private Byte starlevel;

    private Integer area;

    private String location;

    private Double discount;

    private static final long serialVersionUID = 1L;
}