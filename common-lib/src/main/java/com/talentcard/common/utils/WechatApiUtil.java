package com.talentcard.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 17:28
 * @Description: 微信API用的POST请求工具类
 */
public class WechatApiUtil {
    public static JSONObject postRequest(String url, JSONObject jsonObject) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        //exchange方式发送get请求
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, entity, JSONObject.class);
        return responseEntity.getBody();
    }
}
