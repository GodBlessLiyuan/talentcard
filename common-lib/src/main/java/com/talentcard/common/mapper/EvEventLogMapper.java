package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventLogPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvEventLogMapper继承基类
 */
@Mapper
public interface EvEventLogMapper extends BaseMapper<EvEventLogPO, Long> {
    List<EvEventLogPO> findByEventId(@Param("eventId") Long eventId);
}