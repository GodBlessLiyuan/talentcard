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
     * @param name
     * @param workLocation
     * @param sex
     * @param status
     * @return
     */
    List<QueryTalentInfoBO> queryTalentInfo(@Param("name") String name,
                                            @Param("workLocation") String workLocation,
                                            @Param("sex") Byte sex,
                                            @Param("status") Byte status);

    /**
     * 计数
     * 根据eventId查询当前活动报名人数
     * @param eventId
     * @return
     */
    Integer countByEventId( @Param("eventId") Long eventId);
}