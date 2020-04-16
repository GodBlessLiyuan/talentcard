package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserCurrentInfoPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserCurrentInfoMapper继承基类
 */
@Mapper
public interface UserCurrentInfoMapper extends BaseMapper<UserCurrentInfoPO, Long> {

    int updateCategoryByTalentId(Long talentId,String talentCategory);
}