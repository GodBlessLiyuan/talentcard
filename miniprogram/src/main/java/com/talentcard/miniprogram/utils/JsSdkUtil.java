package com.talentcard.miniprogram.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.utils.WechatApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class JsSdkUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsSdkUtil.class);
    @Autowired
    private RedisTemplate redisTemplate;
    private static RedisTemplate myRedis;

    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void ApiTicketInitialize() {
        myRedis = redisTemplate;
    }

    /**
     * @throws WechatException
     * @Description: 申请js-sdk所需要的apiTicket
     */
    public static void applyApiTicket() throws WechatException {
        //拼接url
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                + AccessTokenUtil.getAccessToken() + "&type=wx_card";
        //发送请求
        JSONObject jsonObject = WechatApiUtil.getRequest(url);
        String apiTicket = jsonObject.getString("ticket");
        //判断拿到apiTicket是否为空，若为空，抛异常
        //不为空，则如下，更新apiTicket，且记录当前时间
        if (apiTicket != null) {
            myRedis.opsForValue().set("apiTicket", apiTicket, 100L, TimeUnit.MINUTES);
            logger.info("成功拿到apiTicket：{}", apiTicket);
        } else {
            throw new WechatException("apiTicket拿不到");
        }
    }

    public static String getApiTicket() throws WechatException {
        //100分钟，更换apiTicket
        String apiTicket = (String) myRedis.opsForValue().get("apiTicket");
        if (apiTicket == null || apiTicket.equals("")) {
            JsSdkUtil.applyApiTicket();
            apiTicket = (String) myRedis.opsForValue().get("apiTicket");
        }
        return apiTicket;
    }
}
