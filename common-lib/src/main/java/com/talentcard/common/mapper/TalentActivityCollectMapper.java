package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentActivityCollectPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TalentActivityCollectMapper继承基类
 */
@Mapper
public interface TalentActivityCollectMapper extends BaseMapper<TalentActivityCollectPO, Long> {
    /**
     * 根据openId，一二级目录id查询相关我的收藏信息
     * 返回景区/农家乐的id
     *
     * @param openId
     * @param activityFirstContentId
     * @return
     */
    List<Long> findSecondContentIdByCollect(@Param("openId") String openId,
                                            @Param("activityFirstContentId") Long activityFirstContentId);


    /**
     * 根据openId查询相关我的收藏信息
     *
     * @param openId
     * @return
     */
    List<TalentActivityCollectPO> findMyCollect(@Param("openId") String openId);


    /**
     * 根据openId和一级二级目录id确定某一个
     *
     * @param openId
     * @return
     */
    List<TalentActivityCollectPO> findOne(@Param("openId") String openId,
                                          @Param("activityFirstContentId") Long activityFirstContentId,
                                          @Param("activitySecondContentId") Long activitySecondContentId);

    Integer deleteByFactor(@Param("talentId") Long talentId,
                           @Param("activityFirstContentId") Long activityFirstContentId,
                           @Param("activitySecondContentId") Long activitySecondContentId);
}