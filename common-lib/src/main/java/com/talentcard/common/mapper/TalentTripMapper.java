package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentTripPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TalentTripMapper继承基类
 */
@Mapper
public interface TalentTripMapper extends BaseMapper<TalentTripPO, Long> {

    /**
     * 根据openId，当前时间，状态值，查询未过期的人才旅游信息
     * status 1为领取未检票；2为已核销
     * @param openId
     * @param currentTime
     * @return
     */
    TalentTripPO findOneNotExpired(@Param("openId") String openId,
                                   @Param("currentTime") String currentTime);

    Integer TalentGetTimes(@Param("openId") String openId,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime);

    /**
     * 查找人才的旅游活动的历史
     * @param openId
     * @return
     */
    List<TalentTripPO> getHistory(@Param("openId") String openId);
}