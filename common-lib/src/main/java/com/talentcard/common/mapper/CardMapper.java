package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CardPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * CardMapper继承基类
 */
@Mapper
@Repository
public interface CardMapper extends BaseMapper<CardPO, Long> {
}