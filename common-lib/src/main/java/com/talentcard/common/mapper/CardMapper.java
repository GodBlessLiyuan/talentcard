package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CardPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CardMapper继承基类
 */
@Mapper
public interface CardMapper extends BaseMapper<CardPO, Long> {
    CardPO findDefaultCard();
}