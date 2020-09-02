package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.TalentTypeVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-02 09:50
 * @description
 */
public interface ITalentService {
    /**
     * 获取微信用户信息，类型，会员卡号等
     *
     * @param openId
     * @return
     */
    TalentTypeVO getTalentInfo(String openId);

    /**
     * 清除用户缓存信息
     * @param openId
     */
    void clearRedisCache(String openId);
}
