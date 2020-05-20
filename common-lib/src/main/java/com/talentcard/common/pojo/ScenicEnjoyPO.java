package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_scenic_enjoy
 * @author 
 */
@Data
public class ScenicEnjoyPO implements Serializable {
    private Long seId;

    private Long scenicId;

    private Long cardId;

    private Long categoryId;

    private Integer educationId;

    private Integer titleId;

    private Integer quality;

    private Long thId;

    /**
     * 1：人才卡；2：人才类别；3：人才学历；4：职称；5：职业资格；6：人才荣誉
     */
    private Byte type;

    private static final long serialVersionUID = 1L;
}