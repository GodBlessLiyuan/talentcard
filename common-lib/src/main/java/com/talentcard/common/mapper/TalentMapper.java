package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * TalentMapper继承基类
 */
@Mapper
public interface TalentMapper extends BaseMapper<TalentPO, Long> {
    Integer add(TalentPO talentPO);
    HashMap<String, Object> findRegisterOne(Long openId);

}