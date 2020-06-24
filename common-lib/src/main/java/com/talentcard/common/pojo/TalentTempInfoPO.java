package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_temp_info
 * @author 
 */
@Data
public class TalentTempInfoPO implements Serializable {
    private Long ttiId;

    private String info;

    private String openId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}