package com.talentcard.front.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.front.controller.TalentController;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 14:00
 * @Description: 微信access_token相关
 */
@Data
@Component
public class AccessTokenUtil {
    public static String accessToken;
    public static Long accessTokenCreateTime;
    private static String accessTokenRequest;
    private static String appId;
    private static String appSecret;

    //构造方法执行后的初始化
    @PostConstruct
    public void AccessTokenInitialize() {
        AccessTokenUtil.applyAccessToken();
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
            accessToken = applyAccessToken;
            accessTokenCreateTime = System.currentTimeMillis();
//            System.out.println("成功拿到token：" + accessToken);
//            System.out.println("成功更新创建时间：" + accessTokenCreateTime);
        } else {
            throw new WechatException("token拿不到");
        }
    }

    public static String getAccessToken() throws WechatException {
        //一小时，更换accessToken
        if ((System.currentTimeMillis() - accessTokenCreateTime) > 100 * 60 * 1000) {
            AccessTokenUtil.applyAccessToken();
//            System.out.println("成功更换token");
            //一小时清空存储验证码的hashmap
            for (Iterator<HashMap.Entry<String, Long>> it = TalentController.
                    verifyCodeTime.entrySet().iterator(); it.hasNext(); ) {
                HashMap.Entry<String, Long> item = it.next();
                if ((System.currentTimeMillis() - item.getValue()) >= 5 * 60 * 1000) {
                    it.remove();
                    TalentController.verifyCodeMap.remove(item.getKey());
                }
            }
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

