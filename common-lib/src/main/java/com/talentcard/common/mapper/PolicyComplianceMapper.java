package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyCompliancePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PolicyComplianceMapper继承基类
 */
@Mapper
public interface PolicyComplianceMapper extends BaseMapper<PolicyCompliancePO, Long> {
}