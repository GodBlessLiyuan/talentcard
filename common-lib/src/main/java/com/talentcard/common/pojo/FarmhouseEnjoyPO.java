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

    private Long educationId;

    private Long titleId;

    private Long quality;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}