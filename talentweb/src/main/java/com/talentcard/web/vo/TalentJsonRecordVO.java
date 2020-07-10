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
    /**
     * type 1：普通认证
     * type 2：新增认证
     */
    private Byte type;
    private TalentCertificationRecordVO talentCertificationRecordVO;
    private TalentInsertCertificationRecordVO talentInsertCertificationRecordVO;
}
