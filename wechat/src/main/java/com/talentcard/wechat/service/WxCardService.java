package com.talentcard.wechat.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-20 9:38
 * @description: TODO
 * @version: 1.0
 */
public interface WxCardService {
    public Map<String,String> parseRequest(HttpServletRequest request);

    public boolean check(String timestamp,String nonce,String signature);

    public  String getResponse(Map<String,String> requestMap);
}
