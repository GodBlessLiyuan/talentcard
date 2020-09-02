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

    /**
     *
     * 前台活动总查询
     * 判断当前享受群体是否有满足
     * @param cardId
     * @param categoryList
     * @param educationList
     * @param titleList
     * @param qualityList
     * @param honourList
     * @return
     */
    List<Long> findAllEventByFactor(@Param("cardId") Long cardId,
                                     @Param("categoryList") List<Long> categoryList,
                                     @Param("educationList") List<Long> educationList,
                                     @Param("titleList") List<Long> titleList,
                                     @Param("qualityList") List<Long> qualityList,
                                     @Param("honourList") List<Long> honourList);

}