package com.talentcard.web.vo;

import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.pojo.CertApprovalPassRecordPO;
import com.talentcard.common.pojo.CertExamineRecordPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:22 2020/8/25
 * @ Description：获取certID认证id
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Data
public class CerIdVO implements Serializable {
    //认证id
    Long cid;
    //人才id
    Long tid;

    /**
     * PO转VO
     *
     * @param po
     * @return
     */
    public static CerIdVO convert(CertExamineRecordPO po) {
        CerIdVO vo = new CerIdVO();
        vo.setCid(po.getCertId());
        vo.setTid(po.getTalentId());
        return vo;
    }
}
