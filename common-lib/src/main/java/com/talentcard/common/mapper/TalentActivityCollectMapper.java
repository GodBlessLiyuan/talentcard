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
     * @param openId
     * @param activityFirstContentId
     * @return
     */
    List<Long> findMyCollect(@Param("openId")String openId,
                             @Param("activityFirstContentId")Long activityFirstContentId);
}