package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO, Long> {
    int insertReturnId(UserPO userPO);
}