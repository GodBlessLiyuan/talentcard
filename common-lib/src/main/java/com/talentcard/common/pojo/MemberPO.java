package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card_member
 * @author 
 */
@Data
public class MemberPO implements Serializable {
    /**
     * 人才卡id
     */
    private Long tcId;

    /**
     * 会员卡类别编号
     */
    private Long cardTypeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 会员卡状态
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

    private static final long serialVersionUID = 1L;
}