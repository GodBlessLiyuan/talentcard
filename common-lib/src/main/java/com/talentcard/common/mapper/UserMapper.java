package com.talentcard.common.mapper;

import com.talentcard.common.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<User, Long> {
}