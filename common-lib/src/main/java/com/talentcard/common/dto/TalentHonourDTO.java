package com.talentcard.common.dto;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:32
 * @description
 */
@Data
public class TalentHonourDTO {
    private Long insertTalentHonourId;
    private Long insertCertId;
    private Long honourId;
    private String honourPicture;
    private String honourPicture2;
    private String honourPicture3;
    private String info;
    private String openId;
    private Long thId;
    private String opinion;
}
