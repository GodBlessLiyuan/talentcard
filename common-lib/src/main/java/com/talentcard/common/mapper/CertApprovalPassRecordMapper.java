package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CertApprovalPassRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * CertApprovalPassRecordMapper继承基类
 */
@Mapper
public interface CertApprovalPassRecordMapper extends BaseMapper<CertApprovalPassRecordPO, Long> {
    CertApprovalPassRecordPO selectByCertId(@Param("certId") Long certId);
}