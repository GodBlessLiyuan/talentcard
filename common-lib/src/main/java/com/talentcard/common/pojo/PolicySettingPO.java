package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * po_setting
 * @author 
 */
@Data
public class PolicySettingPO implements Serializable {
    /**
     * 政策适配人群属性id
     */
    private Long pSetingid;

    /**
     * 人才政策ID
     */
    private Long policyId;

    /**
     * 人才卡ID
     */
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

    /**
     * 人才荣誉id
     */
    private Long honourId;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}