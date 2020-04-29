package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CardPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * CardMapper继承基类
 */
@Mapper
public interface CardMapper extends BaseMapper<CardPO, Long> {
    /**
     * 查找基本卡信息
     * @return
     */
    CardPO findDefaultCard();

    /**
     * 判断该卡是否存在成员，决定是否能够删卡
     * @param cardId
     * @return
     */
    Integer findIfExistMember(Long cardId);

    /**
     * 根据对应条件查询
     * @param hashMap
     * @return
     */
    List<CardPO> findByFactor (HashMap<String, Object> hashMap);

    /**
     * 查询高级卡
     * @param hashMap
     * @return
     */
    List<CardPO> findSeniorCard (HashMap<String, Object> hashMap);

    /**
     * 根据高级卡卡片id获得当前卡片id和其对应name
     * @return
     */
    List<CardPO> queryCardIdName();
}