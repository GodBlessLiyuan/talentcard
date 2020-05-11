package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentTripPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentTripMapper继承基类
 */
@Mapper
public interface TalentTripMapper extends BaseMapper<TalentTripPO, Long> {
}