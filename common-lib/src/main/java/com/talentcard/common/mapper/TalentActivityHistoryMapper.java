package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentActivityHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TalentActivityHistoryMapper继承基类
 */
@Mapper
public interface TalentActivityHistoryMapper extends BaseMapper<TalentActivityHistoryPO, Long> {
    List<TalentActivityHistoryPO> findByOpenId(@Param("openId") String openId);
}