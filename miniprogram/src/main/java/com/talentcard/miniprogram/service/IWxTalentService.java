package com.talentcard.miniprogram.service;

import com.talentcard.common.pojo.UserCardPO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/5/25 19:28
 * @description: 微信用户服务管理
 * @version: 1.0
 */
public interface IWxTalentService {


    /**
     * 判断人卡表里是否已经有待审批的
     *
     * @param openId
     * @return
     */
    Integer findUserCardExist(String openId);

    /**
     * 根据openId和uc的状态值，找这个用户当前使用的卡或者待领取的卡信息
     * uc status=1和2分别最多有一条
     *
     * @param openId
     * @return
     */
    Map<String, Object> findCurrentCard(@Param("openId") String openId, @Param("status") Byte status);

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

    /**
     * 根据talentId和cardId更新status
     * @param po
     * @return
     */
    int updateStatusByTalentId(UserCardPO po);

    /**
     * 根据卡号查询openId
     */
    String findOpenIdByCardNum(@Param("cardNum") String cardNum);

    /**
     * 清除缓存
     * @param openId
     */
    void cleanRedisCache(String openId);
}
