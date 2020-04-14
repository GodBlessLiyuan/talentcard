package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserCardPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserCardMapper继承基类
 */
@Mapper
public interface UserCardMapper extends BaseMapper<UserCardPO, Long> {
    Integer findUserCardExist(String openId);
}