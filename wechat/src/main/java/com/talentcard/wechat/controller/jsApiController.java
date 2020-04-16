package com.talentcard.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.pojo.JsTokenPO;
import com.talentcard.wechat.utils.CommonUtil;
import com.talentcard.wechat.utils.JsApiTicketUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RequestMapping("jsApi")
@RestController
public class jsApiController {
    private static String appId;
    private static String appSecret;

    /**
     * 获取jsApiTicket
     *
     * @return
     */
    @GetMapping("getTicket")
    public ResultVO jsApiTicket() {
        String jsApiTicket = JsApiTicketUtil.getJsApiTicket();
        if (jsApiTicket == null || jsApiTicket == "") {
            return new ResultVO(2201);
        }
        return new ResultVO(1000, jsApiTicket);
    }

    /**
     * 签名算法
     *
     * @return
     */
    @PostMapping("getSignature")
    public ResultVO getSignature(@RequestParam(value = "url") String url) {
        String noncestr = CommonUtil.getRandomString(8);
        String jsApiTicket = JsApiTicketUtil.getJsApiTicket();
        String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));

        String signature = JsApiTicketUtil.getSignature(noncestr, jsApiTicket, timeStamp, url);
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("noncestr", noncestr);
        hashMap.put("jsApiTicket", jsApiTicket);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("url", url);
        hashMap.put("signature", signature);
        return new ResultVO(1000, hashMap);
    }

    /**
     * 通过code换取网页授权Js的access_token
     *
     * @return
     */
    @GetMapping("getJsToken")
    public ResultVO getJsToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
                + "&secret=" + appSecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        JSONObject jsonObject = new JSONObject();
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        //exchange方式发送get请求
        JsTokenPO jsTokenPO = restTemplate.getForObject(url, JsTokenPO.class);
        return new ResultVO(1000, jsTokenPO);
    }

    @Value("${wechat.appId}")
    private void setAppId(String appId) {
        jsApiController.appId = appId;
    }

    @Value("${wechat.appSecret}")
    private void setAppSecret(String appSecret) {
        jsApiController.appSecret = appSecret;
    }
}
