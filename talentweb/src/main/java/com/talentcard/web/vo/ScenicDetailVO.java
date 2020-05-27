package com.talentcard.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 9:19
 * @description: TODO
 * @version: 1.0
 */
@Data
public class ScenicDetailVO implements Serializable {
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
    private List<Long> cardIds;

    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private List<Long> categoryIds;

    /**
     * 可查看与申请此景区的人才学历IDs
     */
    private List<Integer> educIds;

    /**
     * 可查看与申请此景区的人才职称IDs
     */
    private List<Integer> titleIds;

    /**
     * 可查看与申请此景区的人才职业资格IDs
     */
    private List<Integer> qualityIds;

    /**
     * 景区头图
     */
    private String avatar;

    /**
     * 景区轮播图
     */
    private List<String> picture;

    /**
     * 景区介绍信息
     */
    private String desc;

    /**
     * 备注
     */
    private String extra;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 人才荣誉IDs
     */
    private List<Long> talentHonourIds;
}
