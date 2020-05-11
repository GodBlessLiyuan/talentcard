package com.talentcard.common.mapper;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserCurrentInfoMapper继承基类
 */
@Mapper
public interface UserCurrentInfoMapper extends BaseMapper<UserCurrentInfoPO, Long> {

    /**
     * 根据talentId，更新uci表中的人才类别
     * @param talentId
     * @param talentCategory
     * @return
     */
    int updateCategoryByTalentId(@Param("talentId") Long talentId, @Param("talentCategory") String talentCategory);

    /**
     * 根据talentId查找
     * @param talentId
     * @return
     */
    UserCurrentInfoPO selectByTalentId(@Param("talentId") Long talentId);
}