package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_user_current_info
 * @author 
 */
@Data
public class UserCurrentInfoPO implements Serializable {
    private Long uciId;

    private Long talentId;

    private Byte political;

    private Integer education;

    private String school;

    /**
     * 1：是；2：否
     */
    private Byte firstClass;

    private String major;

    private Integer ptCategory;

    private String ptInfo;

    private Integer pqCategory;

    private String pqInfo;

    private String talentCategory;

    private Long honourId;

    private String thInfo;

    private static final long serialVersionUID = 1L;
}