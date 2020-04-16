package com.talentcard.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/09 17:28
 * @Description: 微信API用的请求工具类
 */
public class WechatApiUtil {
    /**
     *@Description: post request
     * @param url
     * @param jsonObject
     * @return
     */
    public static JSONObject postRequest(String url, JSONObject jsonObject) throws WechatException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        //exchange方式发送get请求
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST, entity, JSONObject.class);
        return responseEntity.getBody();
    }

    /**
     * @Description: get request
     * @param url
     * @return
     */
    public static JSONObject getRequest(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //表单形式发送
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject jsonObject = new JSONObject();
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        //exchange方式发送get请求
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, entity, JSONObject.class);
        return responseEntity.getBody();
    }
}
