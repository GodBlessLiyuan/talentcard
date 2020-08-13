package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PolicyTypeMapper继承基类
 */
@Mapper
public interface PolicyTypeMapper extends BaseMapper<PolicyTypePO, Long> {
}