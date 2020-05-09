package com.talentcard.web.dto;

import com.talentcard.common.pojo.ScenicPO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:05
 * @description: 景区
 * @version: 1.0
 */
@Data
public class ScenicDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 景区ID
     */
    private Long scenicId;

    /**
     * 景区名称
     */
    private String name;

    /**
     * 入园限制 - 频
     */
    private Integer rate;

    /**
     * 入园限制 - 单位； 1：年；2：季；3：月
     */
    private Byte unit;

    /**
     * 入园限制 - 次
     */
    private Integer times;

    /**
     * 可查看与申请此景区的人才卡IDs
     */
    private String[] cardIds;

    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private String[] categoryIds;

    /**
     * 可查看与申请此景区的人才学历IDs
     */
    private String[] educIds;

    /**
     * 可查看与申请此景区的人才职称IDs
     */
    private String[] titleIds;

    /**
     * 可查看与申请此景区的人才职业资格IDs
     */
    private String[] qualityIds;

    /**
     * 景区头图
     */
    private String avatar;

    /**
     * 景区轮播图
     */
    private String[] picture;

    /**
     * 景区介绍信息
     */
    private String desc;

    /**
     * 备注
     */
    private String extra;
}
