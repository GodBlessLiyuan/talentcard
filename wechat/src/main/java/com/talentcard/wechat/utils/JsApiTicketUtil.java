package com.talentcard.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.utils.WechatApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class JsApiTicketUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsApiTicketUtil.class);
    @Autowired
    private RedisTemplate redisTemplate;
    private static RedisTemplate myRedis;

    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void JsApiTicketInitialize() {
        myRedis = redisTemplate;
    }

    /**
     * @throws WechatException
     * @Description: 申请jsApiTicket
     */
    public static void applyJsApiTicket() throws WechatException {
        //拼接url
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
                + AccessTokenUtil.getAccessToken() + "&type=jsapi";
        //发送请求
        JSONObject jsonObject = WechatApiUtil.getRequest(url);
        String jsApiTicket = jsonObject.getString("ticket");
        //判断拿到jsApiTicket是否为空，若为空，抛异常
        //不为空，则如下，更新jsApiTicket，且记录当前时间
        if (jsApiTicket != null) {
            myRedis.opsForValue().set("jsApiTicket", jsApiTicket, 100L, TimeUnit.MINUTES);
            logger.info("成功拿到jsApiTicket：{}", jsApiTicket);
        } else {
            throw new WechatException("jsApiTicket拿不到");
        }
    }

    public static String getJsApiTicket() throws WechatException {
        //100分钟，更换jsApiTicket
        String jsApiTicket = (String) myRedis.opsForValue().get("jsApiTicket");
        if (jsApiTicket == null || jsApiTicket == "") {
            JsApiTicketUtil.applyJsApiTicket();
            jsApiTicket = (String) myRedis.opsForValue().get("jsApiTicket");
        }
        return jsApiTicket;
    }

    /**
     * 根据四个参数获得签名
     * @param noncestr
     * @param jsApiTicket
     * @param timestamp
     * @param url
     * @return
     */
    public static String getSignature(String noncestr, String jsApiTicket, String timestamp, String url) {
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{noncestr, jsApiTicket, timestamp, url};
        Arrays.sort(strs);
        //2）将四个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2]+strs[3];
        String signature = CommonUtil.sha1(str);
        return signature;
    }
}
