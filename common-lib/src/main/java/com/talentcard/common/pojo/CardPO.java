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

    private String name;

    private String title;

    private Long currNum;

    private String description;

    private String picture;

    private String pictureCdn;

    private String prerogative;

    private String initialWord;

    private String initialNum;

    private Date createTime;

    /**
     * 1：默认；2：非默认
     */
    private Byte status;

    private String brandName;

    private static final long serialVersionUID = 1L;
}