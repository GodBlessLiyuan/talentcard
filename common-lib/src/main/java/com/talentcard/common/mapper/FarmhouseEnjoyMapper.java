package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseEnjoyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FarmhouseEnjoyMapper继承基类
 */
@Mapper
public interface FarmhouseEnjoyMapper extends BaseMapper<FarmhouseEnjoyPO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<FarmhouseEnjoyPO> pos);

    /**
     * 根据 farmhouseId 删除
     *
     * @param farmhouseId
     */
    void deleteByFarmhouseId(Long farmhouseId);

    /**
     * 根据 farmhouseId 查询
     *
     * @param farmhouseId
     * @return
     */
    List<FarmhouseEnjoyPO> queryByFarmhouseId(Long farmhouseId);


    /**
     *
     * @param cardId
     * @param categoryList
     * @param educationList
     * @param titleList
     * @param qualityList
     * @param honourList
     * @return
     */
    List<Long> findSecondContent(@Param("cardId") Long cardId,
                                 @Param("categoryList") List<Long> categoryList,
                                 @Param("educationList") List<Long> educationList,
                                 @Param("titleList") List<Long> titleList,
                                 @Param("qualityList") List<Long> qualityList,
                                 @Param("honourList") List<Long> honourList);
}
