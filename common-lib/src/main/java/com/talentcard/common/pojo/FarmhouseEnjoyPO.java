package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_farmhouse_enjoy
 * @author 
 */
@Data
public class FarmhouseEnjoyPO implements Serializable {
    private Long feId;

    private Long farmhouseId;

    private Long cardId;

    private Long categoryId;

    private Integer educationId;

    private Integer titleId;

    private Integer quality;

    private Long honourId;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}