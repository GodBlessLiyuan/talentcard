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

    /**
     * 根据员工openID,一级目录id和二级目录id
     * 得到检验通过次数
     * @param activityFirstContentId
     * @param activitySecondContentId
     * @param startTime
     * @param endTime
     * @return
     */
    Long getVertifyNum( @Param("staffId") Long staffId,
                       @Param("activityFirstContentId") Long activityFirstContentId,
                       @Param("activitySecondContentId") Long activitySecondContentId,
                       @Param("startTime") String startTime,
                       @Param("endTime") String endTime);
}