package com.talentcard.common.mapper;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.pojo.TalentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TalentMapper继承基类
 */
@Mapper
public interface TalentMapper extends BaseMapper<TalentPO, Long> {
    Integer add(TalentPO talentPO);
    HashMap<String, Object> findRegisterOne(String openId);
    List<TalentBO> findOne(HashMap<String, Object> hashMap);
    TalentPO selectByOpenId(String openId);
    String findCardId(String openId);

    /**
     * 根据信息检索符合条件的人才信息
     * @param map
     * @return
     */
    List<TalentCertStatusBO> queryTalentStatus(Map<String, Object> map);
}