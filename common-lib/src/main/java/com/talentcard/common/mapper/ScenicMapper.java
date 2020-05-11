package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ScenicPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ScenicMapper继承基类
 */
@Mapper
public interface ScenicMapper extends BaseMapper<ScenicPO, Long> {
    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    ScenicPO queryByName(String name);

    /**
     * 根据scenicId 跟新 status
     *
     * @param scenicId
     * @param status
     */
    void updateStatus(@Param("scenicId") Long scenicId, @Param("status") Long status);
}