package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_test_talent_info
 * @author 
 */
@Data
public class TestTalentInfoPO implements Serializable {
    private Long ttiId;

    private String openId;

    private String primaryCardNum;

    private String seniorCardNum;

    private static final long serialVersionUID = 1L;
}