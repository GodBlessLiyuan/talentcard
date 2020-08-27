package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoStatisticsPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PoStatisticsMapper继承基类
 */
@Mapper
public interface PoStatisticsMapper extends BaseMapper<PoStatisticsPO, Long> {
    /**
     * 删除所有的统计数据
     * @return
     */
    int deleteAll();
}