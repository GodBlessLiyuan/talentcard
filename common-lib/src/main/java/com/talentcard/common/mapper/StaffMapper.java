package com.talentcard.common.mapper;

import com.talentcard.common.bo.StaffTripBO;
import com.talentcard.common.pojo.StaffPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * @param activityFirstContent
     * @param activitySecondContent
     * @return
     */
    Integer findStaffNum(@Param("activityFirstContent") Long activityFirstContent,
                         @Param("activitySecondContent") Long activitySecondContent);

    /**
     * 查找旅游员工信息
     */
    StaffTripBO findOne(@Param("openId") String openId);
}