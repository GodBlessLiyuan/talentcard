package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoCompliancePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PoComplianceMapper继承基类
 */
@Mapper
public interface PoComplianceMapper extends BaseMapper<PoCompliancePO, Long> {
}