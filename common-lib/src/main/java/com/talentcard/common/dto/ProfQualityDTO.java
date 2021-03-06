package com.talentcard.common.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:32
 * @description
 */
@Data
public class ProfQualityDTO {
    private Long insertQualityId;
    private Long insertCertId;
    private Integer category;
    private String picture;
    private String picture2;
    private String picture3;
    private String info;
    private String openId;
    private Long pqId;
    private String opinion= "";
}
