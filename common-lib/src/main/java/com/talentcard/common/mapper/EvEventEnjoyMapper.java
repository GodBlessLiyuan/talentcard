package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventEnjoyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvEventEnjoyMapper继承基类
 */
@Mapper
public interface EvEventEnjoyMapper extends BaseMapper<EvEventEnjoyPO, Long> {
    /**
     * 根据eventId删除相关所有信息
     * @param eventId
     */
    Integer deleteByEventId(@Param("eventId")Long eventId);

    /**
     * 根据eventId删除相关所有信息
     * @param eventId
     */
    List<EvEventEnjoyPO> findByEventId(@Param("eventId")Long eventId);

}