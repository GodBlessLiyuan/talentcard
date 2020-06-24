package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentTempInfoPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentTempInfoMapper继承基类
 */
@Mapper
public interface TalentTempInfoMapper extends BaseMapper<TalentTempInfoPO, Long> {
}