package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoStatisticsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PoStatisticsMapper继承基类
 */
@Mapper
public interface PoStatisticsMapper extends BaseMapper<PoStatisticsPO, Long> {
}