package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user_card
 * @author 
 */
@Data
public class UserCardPO implements Serializable {
    private Long ucId;

    private Long talentId;

    private Long cardId;

    private String name;

    private String num;

    private String currentNum;

    private Date createTime;

    /**
     * 1 待领卡
2 已领卡，使用中
3 废弃
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}