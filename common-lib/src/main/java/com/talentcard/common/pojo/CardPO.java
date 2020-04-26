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

    private String wxCardId;

    private String name;

    private String title;

    private Long memberNum;

    private Long waitingMemberNum;

    private Long currNum;

    private String description;

    private String picture;

    private String pictureCdn;

    private String logoUrl;

    private String prerogative;

    private String initialWord;

    private String initialNum;

    private Date createTime;

    /**
     * 1：默认；2：非默认
     */
    private Byte status;

    /**
     * 1正在使用
2删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}