package com.talentcard.common.mapper;

import com.talentcard.common.pojo.StaffTripPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * StaffTripMapper继承基类
 */
@Mapper
public interface StaffTripMapper extends BaseMapper<StaffTripPO, Long> {
    /**
     * 查找当前活动的注册人数
     * @param scenicId
     * @return
     */
    int findStaffNum(@Param("scenicId") Long scenicId);
}