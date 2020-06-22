package com.talentcard.common.mapper;

import com.talentcard.common.pojo.InsertEducationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * InsertEducationMapper继承基类
 */
@Mapper
public interface InsertEducationMapper extends BaseMapper<InsertEducationPO, Long> {
}