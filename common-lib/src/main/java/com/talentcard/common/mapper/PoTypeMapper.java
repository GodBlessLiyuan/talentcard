package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoTypePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PoTypeMapper继承基类
 */
@Mapper
public interface PoTypeMapper extends BaseMapper<PoTypePO, Long> {
}