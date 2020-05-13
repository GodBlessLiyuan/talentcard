package com.talentcard.common.mapper;

import com.talentcard.common.pojo.StaffPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * StaffMapper继承基类
 */
@Mapper
public interface StaffMapper extends BaseMapper<StaffPO, Long> {
    /**
     * 判断staff表是否存已经存在该openId
     *
     * @param openId
     * @return
     */
    StaffPO findOneByOpenId(@Param("openId") String openId);

    /**
     * 根据一级和二级目录查找特定活动员工存在的人数
     *
     * @param activityFirstContentId
     * @param activitySecondContentId
     * @return
     */
    Integer findStaffNum(@Param("activityFirstContentId") Long activityFirstContentId,
                         @Param("activitySecondContentId") Long activitySecondContentId);

    List<StaffPO> findStaffByFactor(HashMap<String, Object> hashMap);

    /**
     * 根据员工openID,一级目录id和二级目录id
     * 得到检验通过次数
     * @param openId
     * @param activityFirstContentId
     * @param activitySecondContentId
     * @param startTime
     * @param endTime
     * @return
     */
    Long getVertifyNum(@Param("openId") String openId,
                       @Param("activityFirstContentId") Long activityFirstContentId,
                       @Param("activitySecondContentId") Long activitySecondContentId,
                       @Param("startTime") String startTime,
                       @Param("endTime") String endTime);
}