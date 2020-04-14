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

    private Integer ptCategory;

    private String ptInfo;

    private Integer pqCategory;

    private String pqInfo;

    private static final long serialVersionUID = 1L;
}