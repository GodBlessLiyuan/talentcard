package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_talent_json_record
 * @author 
 */
@Data
public class TalentJsonRecordPO implements Serializable {
    private Long ttiId;

    private String info;

    private String openId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}