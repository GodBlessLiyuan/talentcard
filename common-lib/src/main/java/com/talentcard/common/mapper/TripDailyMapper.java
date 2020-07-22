package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TripDailyPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TripDailyMapper继承基类
 */
@Mapper
public interface TripDailyMapper extends BaseMapper<TripDailyPO, Long> {
    int batchInsert(List<TripDailyPO> tripDailyPOS);

    TripDailyPO getBySidDaily(String sidDaily);
}