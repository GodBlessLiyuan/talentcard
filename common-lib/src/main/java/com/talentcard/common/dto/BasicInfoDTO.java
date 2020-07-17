package com.talentcard.common.dto;

import lombok.Data;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:33
 * @description
 */
@Data
public class BasicInfoDTO {
    private String openId;
    private String workUnit;
    private Integer industry;
    private String phone;
    private Byte political;
    private String workLocation;
    private Byte workLocationType;
    private String opinion;
}
