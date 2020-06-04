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
import java.util.*;
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
            logger.info("jsApiTicket拿不到报错：{}", jsonObject.toString());
            throw new WechatException("jsApiTicket拿不到");
        }
    }

    public static String getJsApiTicket() throws WechatException {
        //100分钟，更换jsApiTicket
        String jsApiTicket = (String) myRedis.opsForValue().get("jsApiTicket");
        if (jsApiTicket == null || jsApiTicket.equals("")) {
            JsApiTicketUtil.applyJsApiTicket();
            jsApiTicket = (String) myRedis.opsForValue().get("jsApiTicket");
        }
        return jsApiTicket;
    }

    /**
     * 微信 JS 接口签名校验
     * 根据四个参数获得签名
     *
     * @param noncestr
     * @param jsApiTicket
     * @param timestamp
     * @param url
     * @return
     */
    public static String getSignature(String noncestr, String jsApiTicket, String timestamp, String url) {
        //1）将token、timestamp、nonce三个参数进行字典序排序
        HashMap hashMap = new HashMap(4);
        hashMap.put("noncestr", noncestr);
        hashMap.put("jsapi_ticket", jsApiTicket);
        hashMap.put("timestamp", timestamp);
        hashMap.put("url", url);
        String str = sort(hashMap);
        String signature = CommonUtil.sha1(str);
        return signature;
    }

    /**
     * 生成签名，根据字段名ascii码，从小大到大
     *
     * @param info
     * @return
     */
    public static String sort(Map<String, String> info) {
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(info.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> arg0, Map.Entry<String, String> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String ret = "";
        for (Map.Entry<String, String> entry : infoIds) {
            ret += entry.getKey();
            ret += "=";
            ret += entry.getValue();
            ret += "&";
        }
        ret = ret.substring(0, ret.length() - 1);
        return ret;
    }
}
