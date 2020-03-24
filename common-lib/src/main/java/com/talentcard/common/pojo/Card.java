package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card_type
 * @author 
 */
@Data
public class Card implements Serializable {
    /**
     * 会员卡类别编号
     */
    private Long cardTypeId;

    /**
     * 卡名
     */
    private String cardName;

    /**
     * 人才卡标题
     */
    private String cardTitle;

    /**
     * 初始卡片编号
     */
    private String initialCardNumber;

    /**
     * 卡片描述
     */
    private String cardDescription;

    /**
     * 卡片背景图
     */
    private String backgroundPictureUrl;

    /**
     * 状态 : 人才卡第一次申请状态
审批中
使用中
已废弃
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date upadateTime;

    /**
     * 是否删除
     */
    private Byte dr;

    /**
     * logo图标
     */
    private String logoUrl;

    /**
     * 会员卡特权说明
     */
    private String prerogative;

    /**
     * 商户名字
     */
    private String brandName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 规则
     */
    private String bonusRule;

    /**
     * 剩余时间
     */
    private Long remaindTime;

    private static final long serialVersionUID = 1L;
}