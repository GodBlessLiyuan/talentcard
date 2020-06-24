package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentCardHoldListPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentCardHoldListMapper继承基类
 */
@Mapper
public interface TalentCardHoldListMapper extends BaseMapper<TalentCardHoldListPO, Long> {
    TalentCardHoldListPO selectByIdCard(String idCard);
}