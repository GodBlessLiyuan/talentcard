package com.talentcard.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:27
 * @description: 政策
 * @version: 1.0
 */
@Data
public class PolicyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 政策权益名称
     */
    private String name;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 是否需要申请
     */
    private String apply;
    /**
     * 频次规则
     */
    private String frequency;
    /**
     * 备注信息
     */
    private String desc;
}
