package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ScenicEnjoyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * ScenicEnjoyMapper继承基类
 */
@Mapper
public interface ScenicEnjoyMapper extends BaseMapper<ScenicEnjoyPO, Long> {
    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<ScenicEnjoyPO> pos);

    /**
     * 根据 scenic id 删除
     *
     * @param scenicId
     */
    void deleteByScenicId(Long scenicId);

    /**
     * 根据 scenic id 查询
     *
     * @param scenicId
     * @return
     */
    List<ScenicEnjoyPO> queryByScenicId(Long scenicId);

    /**
     * 判断当前享受群体是否有满足的景区
     *
     * @param cardId
     * @param categoryList
     * @param education
     * @param title
     * @param quality
     * @param talentHonour
     * @return
     */
    List<Long> findSecondContent(@Param("cardId") Long cardId, @Param("categoryList") List<Long> categoryList,
                                 @Param("education") Integer education, @Param("title") Integer title,
                                 @Param("quality") Integer quality,
                                 @Param("talentHonour") Long talentHonour);
}