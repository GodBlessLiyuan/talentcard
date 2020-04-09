package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PolicyMapper继承基类
 */
@Mapper
public interface PolicyMapper extends BaseMapper<PolicyPO, Long> {
}