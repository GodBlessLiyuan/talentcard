package com.talentcard.common.mapper;

import com.talentcard.common.bo.UserBO;
import com.talentcard.common.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO, Long> {
    int insertReturnId(UserPO userPO);
    UserBO findByWechat(@Param("wechatId")String wechatId, @Param("status")Byte status);
}