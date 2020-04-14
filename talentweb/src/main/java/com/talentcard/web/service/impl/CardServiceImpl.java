package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import com.talentcard.web.utils.AccessTokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Service
@EnableTransactionManagement
public class CardServiceImpl implements ICardService {
    @Override
    public ResultVO add(JSONObject jsonObject) {
        //发送post请求，激活卡套
        try {
            String accessToken = AccessTokenUtil.getAccessToken();
            String url = "https://api.weixin.qq.com/card/membercard/activate?access_token=" + accessToken;
            WechatApiUtil.postRequest(url, jsonObject);
        } catch (WechatException wechatException) {
            return new ResultVO(6666);
        }
        return new ResultVO(1000);
    }
}
