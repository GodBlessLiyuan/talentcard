package com.talentcard.common.mapper;

import com.talentcard.common.pojo.UserCardPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * UserCardMapper继承基类
 */
@Mapper
public interface UserCardMapper extends BaseMapper<UserCardPO, Long> {
    Integer findUserCardExist(String openId);

    HashMap<String, Object> findCurrentCard(String openId);

    /**
     * 根据ucId更改对应的人卡表状态值，1是待使用，2是使用，3是失效
     * 用于卡券的激活接口
     * Chen XU
     *
     * @param talentId
     * @param currentStatus
     * @param status
     * @return
     */
    int updateStatusById(@Param("talentId") Long talentId,
                         @Param("currentStatus") Byte currentStatus, @Param("status") Byte status);
}