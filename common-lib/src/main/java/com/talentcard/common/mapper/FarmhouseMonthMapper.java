package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseMonthPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FarmhouseMonthMapper继承基类
 */
@Mapper
public interface FarmhouseMonthMapper extends BaseMapper<FarmhouseMonthPO, Long> {
    int batchInsert(List<FarmhouseMonthPO> farmhouseMonthPOS);

    List<FarmhouseMonthPO> queryByMonth(String month);
}