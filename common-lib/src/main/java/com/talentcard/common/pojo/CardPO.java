package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_card
 * @author 
 */
@Data
public class CardPO implements Serializable {
    private Long cardId;

    private String title;

    private String name;

    private String initialWord;

    private String initialNum;

    private Long currNum;

    private String description;

    private String picture;

    private Date createTime;

    /**
     * 1：默认；2：非默认
     */
    private Byte status;

    private static final long serialVersionUID = 1L;
}