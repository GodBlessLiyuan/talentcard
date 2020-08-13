package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoTalentTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PoTalentTypeMapper继承基类
 */
@Mapper
public interface PoTalentTypeMapper extends BaseMapper<PoTalentTypePO, Long> {
}