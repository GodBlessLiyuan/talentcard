package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhousePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * FarmhouseMapper继承基类
 */
@Mapper
public interface FarmhouseMapper extends BaseMapper<FarmhousePO, Long> {
    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    FarmhousePO queryByName(String name);

    /**
     * 根据 farmhouseId 跟新 status
     *
     * @param farmhouseId
     * @param status
     */
    void updateStatus(@Param("scenicId") Long farmhouseId, @Param("status") Long status);
}