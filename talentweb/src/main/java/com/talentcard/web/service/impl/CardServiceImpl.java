package com.talentcard.web.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.pojo.wechat.create.MemberCardPO;
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
        /**
         * 1. 实体类或集合转JSON串
         * String jsonString = JSONObject.toJSONString(实体类);
         * 2.JSON串转JSONObject
         * JSONObject jsonObject = JSONObject.parseObject(jsonString);
         */
        MemberCardPO card = new MemberCardPO();
        JSONObject jsonObject1= JSONObject.parseObject(JSONObject.toJSONString(card));
        //发送post请求，激活卡套
        try {
            String url = "https://api.weixin.qq.com/card/create?access_token="
                    + AccessTokenUtil.getAccessToken();
//            WechatApiUtil.postRequest(url, cardObject);
        } catch (WechatException wechatException) {
            return new ResultVO(6666);
        }
        return new ResultVO(1000);
    }
}
