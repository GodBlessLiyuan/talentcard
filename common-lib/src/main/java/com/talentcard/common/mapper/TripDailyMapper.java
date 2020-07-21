package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TripDailyPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TripDailyMapper继承基类
 */
@Repository
public interface TripDailyMapper extends BaseMapper<TripDailyPO, Long> {
    int batchInsert(List<TripDailyPO> tripDailyPOS);
}