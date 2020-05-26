package com.talentcard.front.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/10 19:15
 * @description: 我的权益
 * @version: 1.0
 */
@Data
public class PolicyDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策权益ID
     */
    private Long pid;
    /**
     * 权益名称
     */
    private String name;
    /**
     * 人才名称
     */
    private String tname;
    /**
     * 权益描述
     */
    private String desc;
    /**
     * 是否需要申请 1: 需要； 2：不需要
     */
    private Byte apply;
    /**
     * 是否能申请 1: 能； 2：审核中；3：次数不足
     */
    private Byte right;
    /**
     * 是否需要银行卡 1: 需要； 2：不需要
     */
    private Byte bank;
    /**
     * 是否需要附件 1: 需要； 2：不需要
     */
    private Byte annex;
    /**
     * 提示文案
     */
    private String title;
    /**
     * 顏色
     */
    private String color;

}
