package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-01 11:41
 * @description
 */
@Data
public class MyEventBO {
    private String name;
    private String picture;
    private String date;
    private String time;
    private Byte status;
    private Byte actualStatus;
    private String eventField;
    private Date startTime;
    private Date endTime;
    private Byte upDown;
    private Integer eventQuota;
    private Integer currentNum;
}
