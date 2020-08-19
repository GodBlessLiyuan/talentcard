package com.talentcard.common.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-19 16:21
 * @description
 */
@Data
public class ApplyNumCountDTO {
    private String start;
    private String end;
    private String num;
    private String name;
    private String apply;
    private Byte status;
    private Byte roleType = (byte) 2;
    private Long responsibleUnitId;
    private Long roleId;
}
