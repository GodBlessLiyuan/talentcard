package com.talentcard.wechat.service.impl;

import com.talentcard.common.mapper.UserCardMapper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.IJsSdkService;
import com.talentcard.wechat.utils.CommonUtil;
import com.talentcard.wechat.utils.JsSdkUtil;
import com.talentcard.wechat.utils.WxCardSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JsServiceImpl implements IJsSdkService {
    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public ResultVO getSignature(String openId) {
        HashMap hashMap = userCardMapper.findCurrentCard(openId, (byte) 1);
        //没有待领取的卡，找正在使用的
        if(hashMap==null){
            hashMap = userCardMapper.findCurrentCard(openId, (byte) 2);
        }
        if(hashMap==null){
            return new ResultVO(2500,"查无此人！");
        }
        String apiTicket = JsSdkUtil.getApiTicket();
        String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));
        String cardId = (String) hashMap.get("cardId");
        String code = (String) hashMap.get("code");
        String noncestr = CommonUtil.getRandomString(8);

        WxCardSign signer = new WxCardSign();
        signer.AddData(apiTicket);
        signer.AddData(timeStamp);
        signer.AddData(cardId);
        signer.AddData(code);
        signer.AddData(noncestr);
        String signature = signer.GetSignature();
        HashMap result = new HashMap(7);
        result.put("code", code);
        result.put("timestamp", timeStamp);
        result.put("card_id", cardId);
        result.put("api_ticket", apiTicket);
        result.put("nonce_str", noncestr);
        result.put("signature", signature);
        result.put("openId", openId);
        return new ResultVO(1000, result);
    }
}
