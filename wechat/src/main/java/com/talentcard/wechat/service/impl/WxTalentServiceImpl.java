package com.talentcard.wechat.service.impl;


import com.alibaba.fastjson.JSON;
import com.talentcard.common.mapper.UserCardMapper;
import com.talentcard.common.pojo.UserCardPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.wechat.service.IWxTalentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author: velve
 * @date: Created in 2020/5/25 19:29
 * @description: 更新微信用户的状态和缓存
 * @version: 1.0
 */
@Service
public class WxTalentServiceImpl implements IWxTalentService {

    @Autowired
    RedisMapUtil redisMapUtil;
    @Autowired
    UserCardMapper userCardMapper;

    @Override
    public Integer findUserCardExist(String openId) {
        return userCardMapper.findUserCardExist(openId);
    }

    @Override
    public HashMap<String, Object> findCurrentCard(String openId, Byte status) {

        String redisCache = redisMapUtil.hget(openId, "findCurrentCard_" + status);
        if (!StringUtils.isEmpty(redisCache)) {
            HashMap<String, Object> res = StringToObjUtil.strToObj(redisCache, HashMap.class);
            if (res != null) {
                return res;
            }
        }

        HashMap user_card = userCardMapper.findCurrentCard(openId, status);
        if(user_card!=null){
            redisMapUtil.hset(openId, "findCurrentCard_" + status, JSON.toJSONString(user_card));
        }
        return user_card;
    }

    @Override
    public int updateStatusById(Long talentId, Byte currentStatus, Byte status) {
        return userCardMapper.updateStatusById(talentId, currentStatus, status);
    }

    @Override
    public int updateStatusByTalentId(UserCardPO po) {
        return userCardMapper.updateStatusByTalentId(po);
    }

    @Override
    public String findOpenIdByCardNum(String cardNum) {
        return null;
    }

    @Override
    public void cleanRedisCache(String openId) {
        /**
         * 清除用户缓存
         */
        this.redisMapUtil.del(openId);
    }
}
