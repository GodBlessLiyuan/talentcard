package com.talentcard.common.mapper;

import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.pojo.ScenicPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

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

    /**
     * 查询符合条件的景区
     * @param scenicIdList
     */
    List<ScenicPO> findEnjoyScenic(@Param("scenicIdList") List<Long> scenicIdList,
                                   @Param("name") String name,
                                   @Param("starLevel") Byte starLevel,
                                   @Param("area") Byte area,
                                   @Param("order") Byte order);

    /**
     * 根据目录id得到一个具体信息
     * @param activitySecondContentId
     * @return
     */
    ScenicBO findOne(@Param("activitySecondContentId") Long activitySecondContentId);
}