package com.talentcard.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 18:50 2020/8/13
 * @ Description：政策类型DTO
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class PolicyTypeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 政策类型id
     */
    private Long ptid;
    /**
     * 更新时间
     */
    private Date utime;
    /**
     * 政策类型名称
     */
    private String ptname;
    /**
     * 政策类型互斥id
     */
    private String[] eids;
    /**
     * 状态：1：上架  2：下架
     */
    private byte status;
    /**
     * 删除：1：未删除  2：已删除
     */
    private byte dr;
    /**
     * 描述
     */
    private String desc;
    /**
     * 互斥政策类型名称
     */
    private String[] eptnames;
}
