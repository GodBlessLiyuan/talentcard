package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserCurrentInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserCurrentInfoMapper继承基类
 */
@Mapper
public interface UserCurrentInfoMapper extends BaseMapper<UserCurrentInfoPO, Long> {

    int updateCategoryByTalentId(@Param("talentId") Long talentId,@Param("talentCategory") String talentCategory);
}