package com.talentcard.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * t_talent_type
 * @author 
 */
@Data
public class TalentTypePO implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    private Long talentId;

    private Long cardId;

    /**
     * 人才类别ID
     */
    private Long categoryId;

    /**
     * 学历ID
     */
    private Integer educationId;

    /**
     * 职称ID
     */
    private Integer titleId;

    /**
     * 人才职业资格ID
     */
    private Integer quality;

    private Long honourId;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;

}