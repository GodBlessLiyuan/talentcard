package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-28 10:43
 * @description
 */
@Data
public class QueryTalentInfoBO {
    private String name;
    private Byte sex;
    private String workLocation;
    private String phone;
    private String talentCard;
    private Byte status;
    private Date createTime;
}
