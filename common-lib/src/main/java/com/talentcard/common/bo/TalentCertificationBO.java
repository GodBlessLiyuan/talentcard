package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-30 11:08
 * @description
 */
@Data
public class TalentCertificationBO {
    /**
     * 人才id
     */
    private Long tId;
    private String openId;
    /**
     * 创建时间
     */
    private Date cTime;
    private String name;
    private Byte sex;
    private String educ;
    private String title;
    private String quality;
    private String cTitle;
    private String cInitialWord;
    private String category;
    private String honour;
    private Long cardId;
}
