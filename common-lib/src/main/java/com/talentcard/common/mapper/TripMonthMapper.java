package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TripMonthPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TripMonthMapper继承基类
 */
@Mapper
public interface TripMonthMapper extends BaseMapper<TripMonthPO, Long> {
    int batchInsert(List<TripMonthPO> allTripMonthPOS);

    TripMonthPO getBySidMonth(String sidMonth);
}