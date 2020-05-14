package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentFarmhousePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentFarmhouseMapper继承基类
 */
@Mapper
public interface TalentFarmhouseMapper extends BaseMapper<TalentFarmhousePO, Long> {
}