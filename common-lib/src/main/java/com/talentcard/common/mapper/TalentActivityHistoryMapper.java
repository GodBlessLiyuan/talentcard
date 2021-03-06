package com.talentcard.common.mapper;

import com.talentcard.common.bo.FootprintBO;
import com.talentcard.common.pojo.TalentActivityHistoryPO;
import com.talentcard.common.pojo.TripDailyPO;
import com.talentcard.common.pojo.TripMonthPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TalentActivityHistoryMapper继承基类
 */
@Mapper
public interface TalentActivityHistoryMapper extends BaseMapper<TalentActivityHistoryPO, Long> {
    List<TalentActivityHistoryPO> findByOpenId(@Param("openId") String openId);

    /**
     * 根据员工openID,一级目录id和二级目录id
     * 得到检验通过次数
     *
     * @param activityFirstContentId
     * @param activitySecondContentId
     * @param startTime
     * @param endTime
     * @return
     */
    Long getVertifyNum(@Param("staffId") Long staffId,
                       @Param("activityFirstContentId") Long activityFirstContentId,
                       @Param("activitySecondContentId") Long activitySecondContentId,
                       @Param("startTime") String startTime,
                       @Param("endTime") String endTime);

    /**
     * 根据开始结束时间，决定一定时间段内，消耗的总次数，如景区这种消耗类型的活动等
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Long getCostTimes(@Param("startTime") String startTime,
                      @Param("endTime") String endTime);

    /**
     * 我的足迹
     *
     * @param openId
     * @return
     */
    List<FootprintBO> footprint(@Param("openId") String openId);

    List<HashMap<String, String>> groupDailyByTime();

    List<TripDailyPO> getDailyPOS(Map<String, Object> times);

    Long getFreeOrDiscount(Map<String, Object> times);

    List<TripMonthPO> getMonthPOS(Map<String, Object> times);

    List<HashMap<String, String>> groupMonthByTime();
    /**
     * 旅游的月统计的总人数
     * */
    Long getTotalNumber(Map<String, Object> numbersMap);
}