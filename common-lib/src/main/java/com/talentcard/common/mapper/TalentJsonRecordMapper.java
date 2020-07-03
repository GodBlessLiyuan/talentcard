package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentJsonRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentJsonRecordMapper继承基类
 */
@Mapper
public interface TalentJsonRecordMapper extends BaseMapper<TalentJsonRecordPO, Long> {
}