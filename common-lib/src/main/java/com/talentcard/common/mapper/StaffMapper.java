package com.talentcard.common.mapper;

import com.talentcard.common.pojo.StaffPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * StaffMapper继承基类
 */
@Mapper
public interface StaffMapper extends BaseMapper<StaffPO, Long> {
    /**
     * 新增，且设置主键
     * @param staffPO
     * @return
     */
    int add(StaffPO staffPO);

    /**
     * 判断staff表是否存已经存在该openId
     * @param openId
     * @return
     */
    StaffPO ifExistStaff(@Param("openId") String openId);
}