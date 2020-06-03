package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_banner
 * @author 
 */
@Data
public class BannerPO implements Serializable {
    private Long bannerId;

    private String name;

    private String picture;

    private String jump;

    private Byte type;

    private String extra;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}