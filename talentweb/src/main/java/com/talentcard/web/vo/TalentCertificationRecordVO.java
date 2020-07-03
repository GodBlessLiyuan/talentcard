package com.talentcard.web.vo;

import com.talentcard.common.pojo.*;
import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-03 09:04
 * @description
 */
@Data
public class TalentCertificationRecordVO {
    private Long certId;
    private CertApprovalPO certApprovalPO;
    private EducationPO educationPO;
    private ProfQualityPO profQualityPO;
    private ProfTitlePO profTitlePO;
    private TalentHonourPO talentHonourPO;
}
