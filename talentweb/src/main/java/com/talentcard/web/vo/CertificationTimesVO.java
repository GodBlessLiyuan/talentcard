package com.talentcard.web.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-03 16:43
 * @description
 */
@Data
public class CertificationTimesVO {
    private Integer educationTimes;
    private Integer titleTimes;
    private Integer qualityTimes;
    private Integer honourTimes;
}
