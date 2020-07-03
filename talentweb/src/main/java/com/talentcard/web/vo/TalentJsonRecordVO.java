package com.talentcard.web.vo;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-03 09:10
 * @description
 */
@Data
public class TalentJsonRecordVO {
    private Byte type;
    private TalentCertificationRecordVO talentCertificationRecordVO;
    private TalentInsertCertificationRecordVO talentInsertCertificationRecordVO;
}
