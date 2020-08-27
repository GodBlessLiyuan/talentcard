package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventQueryPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * EvEventQueryMapper继承基类
 */
@Mapper
public interface EvEventQueryMapper extends BaseMapper<EvEventQueryPO, Long> {
}