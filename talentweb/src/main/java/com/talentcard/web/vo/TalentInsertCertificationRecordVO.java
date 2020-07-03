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
public class TalentInsertCertificationRecordVO {
    private Long insertCertId;
    private InsertCertApprovalPO insertCertApprovalPO;
    private InsertEducationPO insertEducationPO;
    private InsertQualityPO insertQualityPO;
    private InsertTitlePO insertTitlePO;
    private InsertHonourPO insertHonourPO;
}
