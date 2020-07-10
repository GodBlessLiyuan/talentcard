package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_edit_talent_record
 * @author 
 */
@Data
public class EditTalentRecordPO implements Serializable {
    private Long etcId;

    private Long talentId;

    private Long userId;

    /**
     * 1 增
2 删
3 改
     */
    private Byte operationType;

    private Byte operationContent;

    private Date createTime;

    private String comment;

    private String beforeJsonRecord;

    private String afterJsonRecord;

    private static final long serialVersionUID = 1L;
}