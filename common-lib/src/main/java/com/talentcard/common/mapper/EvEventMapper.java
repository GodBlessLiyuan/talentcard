package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * EvEventMapper继承基类
 */
@Mapper
public interface EvEventMapper extends BaseMapper<EvEventPO, Long> {
    Integer add(EvEventPO evEventPO);
}