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
    /**
     * insert，且返回主键
     *
     * @param talentPO
     * @return
     */
    Integer add(TalentPO talentPO);

    /**
     * 根据openId，返回相应的talentPO
     *
     * @param openId
     * @return
     */
    TalentPO selectByOpenId(String openId);


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
     * 根据openId，认证表的status和人卡表的status判断：
     * 第一次激活卡（注册领基本卡）
     * 还是之后的领取或者更新高级卡的激活操作
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
     * 根据openId判断是否存在待领取的卡，status=2和4的时候需要换
     *
     * @param openId
     * @return
     */
    Integer ifExistGetCard(String openId);

    /**
     * 根据openId返回待领取的卡的详细信息
     *
     * @param openId
     * @return
     */
    ActivcateBO findGetCard(@Param("openId") String openId);

    /**
     * 查询已认证用户
     *
     * @param reqMap
     * @return
     */
    List<TalentBO> queryCert(Map<String, Object> reqMap);

    /**
     * 根据openId和c表状态决定一条完整的记录，带4表的所有信息
     *
     * @param hashMap
     * @return
     */
    TalentBO findOne(HashMap<String, Object> hashMap);

    /**
     * 根据openId和c表状态决定一条完整的记录，带4表的所有信息
     * status=2 or 5 or 9
     * 注册时基本卡信息完整的一生
     *
     * @param openId
     * @return
     */
    TalentBO findRegisterOne(String openId);

    /**
     * 根据openId和c表status=9是否有数据来判断是否认证过
     * status=9说明基本卡已经作废，证明认证完成
     *
     * @param openId
     * @return
     */
    Integer ifCertificate(String openId);

//    /**
//     * 根据openId用uci表查找当前的基本信息
//     * @param openId
//     * @return
//     */
//    HashMap<String, Object> findBase(String openId);

    /**
     * 身份证唯一性校验
     *
     * @param idCard
     * @return
     */
    Integer idCardIfUnique(String idCard);

    /**
     * 根据openId和c表status=3或者4是否有数据来判断是否在审核状态中，或者是审核成功待领卡
     * status=3说明待审批
     *
     * @param openId
     * @return
     */
    Integer ifInAudit(String openId);

    /**
     * 根据openid查询
     *
     * @param openid
     * @return
     */
    TalentPO queryByOpenid(String openid);
}