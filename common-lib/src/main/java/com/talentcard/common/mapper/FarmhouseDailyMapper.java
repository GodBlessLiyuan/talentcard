package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseDailyPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * FarmhouseDailyMapper继承基类
 */
@Mapper
public interface FarmhouseDailyMapper extends BaseMapper<FarmhouseDailyPO, Long> {
}