package com.talentcard.miniprogram.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-01 10:25
 * @description
 */
@Data
public class EventEnrollDTO {
    private String openId;
    private String name;
    private String phone;
    private Long eventId;
}
