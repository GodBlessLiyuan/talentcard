package com.talentcard.common.mapper;

import com.talentcard.common.bo.QueryTalentInfoBO;
import com.talentcard.common.pojo.EvEventTalentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvEventTalentMapper继承基类
 */
@Mapper
public interface EvEventTalentMapper extends BaseMapper<EvEventTalentPO, Long> {
    /**
     * 人才信息查询
     *
     * @param name
     * @param workLocation
     * @param sex
     * @param status
     * @return
     */
    List<QueryTalentInfoBO> queryTalentInfo(@Param("eventId") Long eventId,
                                            @Param("name") String name,
                                            @Param("workLocation") String workLocation,
                                            @Param("sex") Byte sex,
                                            @Param("status") Byte status);

    /**
     * 计数
     * 根据eventId查询当前活动报名人数
     *
     * @param eventId
     * @return
     */
    Integer countByEventId(@Param("eventId") Long eventId);

    /**
     * 根据活动id和openid判断是否参加
     *
     * @param openId
     * @return
     */
    Integer checkIfEnrollEvent(@Param("openId") String openId,
                               @Param("eventId") Long eventId);
}