package com.talentcard.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:38
 * @description: 政策
 * @version: 1.0
 */
@Data
public class PolicyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策权益名称
     */
    private String name;
    /**
     * 政策权益编号
     */
    private String num;
    /**
     * 政策详细描述
     */
    private String desc;
    /**
     * 可查看与申请此政策权益的人才卡IDs
     */
    private Integer[] cardIds;
    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private Integer[] categoryIds;
    /**
     * 可查看与申请此政策权益的人才学历IDs
     */
    private Integer[] educIds;
    /**
     * 政策权益是否需要申请: 1：需要；2：不需要
     */
    private Byte apply;
    /**
     * 政策权益申请频次 - 频
     */
    private Integer rate;
    /**
     * 政策权益申请频次 - 单位； 1：年；2：季；3：月
     */
    private Byte unit;
    /**
     * 政策权益申请频次 - 次
     */
    private Integer times;
    /**
     * 银行卡信息；1：需要；2：不需要
     */
    private Byte bank;
    /**
     * 附件信息；1：需要；2：不需要
     */
    private Byte annex;
}
