package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.pojo.FarmhouseMonthPO;
import com.talentcard.common.pojo.TalentFarmhousePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

/**
 * TalentFarmhouseMapper继承基类
 */
@Mapper
public interface TalentFarmhouseMapper extends BaseMapper<TalentFarmhousePO, Long> {
    //按天分组
    List<HashMap<String, String>> groupByUpdateTime();
    //按天统计
    List<FarmhouseDailyPO> queryByUpdateTime(Map<String, String> times);
    //按月分组
    List<HashMap<String, String>> groupByMonthUseUpdateTime();
    //按月统计
    List<FarmhouseMonthPO> getMonthCountByUpdateTime(Map<String, String> times);
    //按月的总计
    HashMap<String, Object> queryTotalByUpdateTime(Map<String, Object> map);
}