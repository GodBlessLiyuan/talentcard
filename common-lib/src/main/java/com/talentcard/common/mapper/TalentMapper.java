package com.talentcard.common.mapper;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.pojo.TalentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param map
     * @return
     */
    List<TalentCertStatusBO> queryTalentStatus(Map<String, Object> map);

    /**
     * @param tid 人才ID
     * @return
     */
    TalentBO queryDetail(Long tid);

    /**
     * 根据openId判断是否需要换卡，status=2和4的时候需要换
     *
     * @param openId
     * @return
     */
    Integer ifChangeCard(String openId);

    /**
     * 根据openId，认证表的status和人卡表的status判断：
     * 第一次激活卡（注册领基本卡）
     * 还是之后的领取或者更新高级卡
     *
     * @param openId
     * @param certificationStatus
     * @param userCardStatus
     * @return
     */
    ActivcateBO activate(@Param("openId") String openId,
                         @Param("certificationStatus") Byte certificationStatus,
                         @Param("userCardStatus") Byte userCardStatus);

    /**
     * 是否存在待领取的卡
     *
     * @param openId
     * @return
     */
    ActivcateBO ifExistGetCard(@Param("openId") String openId);

    /**
     * 查询已认证用户
     *
     * @param reqMap
     * @return
     */
    List<TalentBO> queryCert(Map<String, Object> reqMap);
}