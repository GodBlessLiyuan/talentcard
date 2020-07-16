package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.pojo.TalentFarmhousePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TalentFarmhouseMapper继承基类
 */
@Mapper
public interface TalentFarmhouseMapper extends BaseMapper<TalentFarmhousePO, Long> {
    List<HashMap<String, String>> groupByUpdateTime();


    List<FarmhouseDailyPO> queryByUpdateTime(Map<String, String> times);
}