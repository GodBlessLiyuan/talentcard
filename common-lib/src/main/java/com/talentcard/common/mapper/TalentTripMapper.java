package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentTripPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TalentTripMapper继承基类
 */
@Mapper
public interface TalentTripMapper extends BaseMapper<TalentTripPO, Long> {
    /**
     * 查询未过期的人才旅游信息
     *
     * @param openId
     * @param currentTime
     * @return
     */
    TalentTripPO findOneNotExpired(@Param("openId") String openId,
                                   @Param("currentTime") String currentTime);

    Integer TalentGetTimes(@Param("openId") String openId,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime);

}