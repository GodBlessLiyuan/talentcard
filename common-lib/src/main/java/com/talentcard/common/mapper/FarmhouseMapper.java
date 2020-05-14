package com.talentcard.common.mapper;

import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.ScenicPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    void updateStatus(@Param("farmhouseId") Long farmhouseId, @Param("status") Long status);

    /**
     * 查询符合条件的景区
     * @param farmhouseList
     */
    List<FarmhousePO> findEnjoyFarmhouse(@Param("farmhouseList") List<Long> farmhouseList);

    /**
     * 根据目录id得到一个具体信息
     * @param activitySecondContentId
     * @return
     */
    FarmhouseBO findOne(@Param("activitySecondContentId") Long activitySecondContentId);
}