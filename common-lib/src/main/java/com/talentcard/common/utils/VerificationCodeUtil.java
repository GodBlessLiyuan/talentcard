package com.talentcard.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 14:00
 * @Description: 短信验证码相关
 */

@Component
public class VerificationCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(VerificationCodeUtil.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static StringRedisTemplate myRedis;

    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void VerificationCodeInitialize() {
        myRedis = redisTemplate;
        //默认后门手机号和验证码
        myRedis.opsForValue().set("123", "123");
        myRedis.opsForValue().set("18616500998", "123");
    }

    public static void setCode(String phone, String verificationCode) {
        myRedis.opsForValue().set(phone, verificationCode, 5L, TimeUnit.MINUTES);
        logger.info("验证码：{}", verificationCode);
    }

    public static String getCode(String phone) {
        //取出验证码
        return (String) myRedis.opsForValue().get(phone);
    }
}
