package com.talentcard.miniprogram.controller;

import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.pojo.JsTokenPO;
import com.talentcard.miniprogram.utils.CommonUtil;
import com.talentcard.miniprogram.utils.JsApiTicketUtil;
import com.talentcard.miniprogram.utils.WxMappingJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/10 09:28
 * @description jsApi url获取签名和权限
 */
@RequestMapping("jsApi")
@RestController
public class JsApiController {
    private static String appId;
    private static String appSecret;

    @Autowired
    private TalentMapper talentMapper;

    /**
     * 获取jsApiTicket
     *
     * @return
     */
    @GetMapping("getTicket")
    public ResultVO jsApiTicket() {
        String jsApiTicket = JsApiTicketUtil.getJsApiTicket();
        if (jsApiTicket == null || jsApiTicket.equals("")) {
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
        HashMap<String, Object> hashMap = new HashMap(5);
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
    @PostMapping("getJsToken")
    public ResultVO getJsToken(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        JsTokenPO jsTokenPO = restTemplate.getForObject(url, JsTokenPO.class);

        if (null != jsTokenPO && null != jsTokenPO.getUnionid()) {
            TalentPO talentPO = talentMapper.queryByUnionId(jsTokenPO.getUnionid());
            if (null != talentPO) {
                jsTokenPO.setWxOpenId(talentPO.getOpenId());
            }
        }
        return new ResultVO<>(1000, jsTokenPO);
    }

    @Value("${miniProgram.appId}")
    private void setAppId(String appId) {
        JsApiController.appId = appId;
    }

    @Value("${miniProgram.appSecret}")
    private void setAppSecret(String appSecret) {
        JsApiController.appSecret = appSecret;
    }
}
