package com.talentcard.wechat.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.utils.CommonUtil;
import com.talentcard.wechat.utils.JsApiTicketUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("jsApi")
@RestController
public class jsApiController {
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
    @GetMapping("getSignature")
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

}
