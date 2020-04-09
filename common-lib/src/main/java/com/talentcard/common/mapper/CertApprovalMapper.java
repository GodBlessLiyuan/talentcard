package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CertApprovalPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CertApprovalMapper继承基类
 */
@Mapper
public interface CertApprovalMapper extends BaseMapper<CertApprovalPO, Long> {
}