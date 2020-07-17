package com.talentcard.front.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.mapper.CardMapper;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.utils.WechatApiUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 14:00
 * @Description: 微信access_token相关
 */
@Data
@Component
public class CardNumberUtil {
    private static final byte COMMON_TALENT = 1;
    private static final byte VIP = 2;
    private static final byte TEST = 3;

    @Autowired
    private StringRedisTemplate redisTemplate;
    private static StringRedisTemplate myRedis;
    @Autowired
    private CardMapper cMapper;
    private static CardMapper cardMapper;


    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void AccessTokenInitialize() {
        myRedis = redisTemplate;
        cardMapper = cMapper;
    }

    public static Long getCurrNum() throws WechatException {
        String currNumString = myRedis.opsForValue().get("currNum");
        CardPO cardPO = cardMapper.findDefaultCard();
        Long currNum;
        if (StringUtils.isEmpty(currNumString)) {
            currNum = cardPO.getCurrNum();
        } else {
            currNum = Long.parseLong(currNumString);
        }
        currNum++;
        myRedis.opsForValue().set("currNum", currNum.toString(), 30L, TimeUnit.DAYS);
        return currNum - 1;
    }
}

