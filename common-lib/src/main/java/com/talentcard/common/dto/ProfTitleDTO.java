package com.talentcard.common.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:32
 * @description
 */
@Data
public class ProfTitleDTO {
    private Long insertTitleId;
    private Long insertCertId;
    private Integer category;
    private String info;
    private String picture;
    private String openId;
    private Byte status;
}
