package com.talentcard.common.mapper;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.pojo.TalentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * TalentMapper继承基类
 */
@Mapper
public interface TalentMapper extends BaseMapper<TalentPO, Long> {
    Integer add(TalentPO talentPO);
//    HashMap<String, Object> findRegisterOne(String openId);
    List<TalentBO> findOne(HashMap<String, Object> hashMap);
    TalentPO selectByOpenId(String openId);
}