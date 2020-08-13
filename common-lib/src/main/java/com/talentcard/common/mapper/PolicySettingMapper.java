package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicySettingPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PolicySettingMapper继承基类
 */
@Mapper
public interface PolicySettingMapper extends BaseMapper<PolicySettingPO, Long> {
}