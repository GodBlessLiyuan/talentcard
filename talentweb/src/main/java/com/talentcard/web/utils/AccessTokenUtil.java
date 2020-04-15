package com.talentcard.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 14:00
 * @Description: 微信access_token相关
 */
@Data
@Component
public class AccessTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);
    private static String accessTokenRequest;
    private static String appId;
    private static String appSecret;
    @Autowired
    private RedisTemplate redisTemplate;
    private static RedisTemplate myRedis;

    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void AccessTokenInitialize() {
        myRedis = redisTemplate;
        AccessTokenUtil.getAccessToken();
    }

    /**
     * @throws WechatException
     * @Description: 申请access_token
     */
    public static void applyAccessToken() throws WechatException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //拼接url
        String url = accessTokenRequest + "?grant_type=client_credential"
                + "&appid=" + appId + "&secret=" + appSecret;
        JSONObject jsonObject = new JSONObject();
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        //exchange方式发送get请求
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, entity, JSONObject.class);
        String applyAccessToken = responseEntity.getBody().getString("access_token");
        //判断拿到accessToken是否为空，若为空，抛异常
        //不为空，则如下，更新accessToken，且记录当前时间
        if (applyAccessToken != null) {
            myRedis.opsForValue().set("accessToken", applyAccessToken, 100L, TimeUnit.MINUTES);
            logger.info("成功拿到token：{}", applyAccessToken);
//            String accessToken = (String) myRedis.opsForValue().get("accessToken");
//            logger.info("成功存入token：{}", accessToken);
        } else {
            throw new WechatException("token拿不到");
        }
    }

    public static String getAccessToken() throws WechatException {
        //一小时，更换accessToken
        String accessToken = (String) myRedis.opsForValue().get("accessToken");
        if (accessToken == null || accessToken == "") {
            AccessTokenUtil.applyAccessToken();
        }
        return accessToken;
    }

    @Value("${wechat.appId}")
    private void setAppId(String appId) {
        AccessTokenUtil.appId = appId;
    }

    @Value("${wechat.accessTokenRequest}")
    private void setGetAccessTokenURL(String accessTokenRequest) {
        AccessTokenUtil.accessTokenRequest = accessTokenRequest;
    }

    @Value("${wechat.appSecret}")
    private void setAppSecret(String appSecret) {
        AccessTokenUtil.appSecret = appSecret;
    }
}

