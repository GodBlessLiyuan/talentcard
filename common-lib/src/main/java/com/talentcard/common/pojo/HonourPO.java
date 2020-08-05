package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_honour
 * @author 
 */
@Data
public class HonourPO implements Serializable {
    private Long honourId;

    private String name;

    private String description;

    /**
     * 1上架；2下架
     */
    private Byte status;

    private Date createTime;

    private Date updateTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}