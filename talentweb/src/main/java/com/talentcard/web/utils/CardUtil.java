package com.talentcard.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.pojo.wechat.create.BaseInfoPO;
import com.talentcard.common.pojo.wechat.create.CustomCell1PO;
import com.talentcard.common.pojo.wechat.create.MemberCardPO;
import com.talentcard.common.pojo.wechat.create.WxCardPO;
import com.talentcard.common.vo.ResultVO;
import org.springframework.stereotype.Component;

@Component
public class CardUtil {
    public static ResultVO create(JSONObject jsonObject) {
        /**
         * 1. 实体类或集合转JSON串
         * String jsonString = JSONObject.toJSONString(实体类);
         * 2.JSON串转JSONObject
         * JSONObject jsonObject = JSONObject.parseObject(jsonString);
         * setBackground_pic_url 和 setLogo_url先写死
         *
         */
        WxCardPO card = new WxCardPO();

        MemberCardPO memberCardPO = new MemberCardPO();

        memberCardPO.setBackground_pic_url("https://mmbiz.qlogo.cn/mmbiz/");
        BaseInfoPO baseInfoPO = new BaseInfoPO();
        baseInfoPO.setLogo_url("http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZ/0");
        baseInfoPO.setBrand_name(jsonObject.getString(""));
        baseInfoPO.setCode_type(jsonObject.getString(""));
        baseInfoPO.setTitle(jsonObject.getString(""));
        baseInfoPO.setColor(jsonObject.getString(""));
        baseInfoPO.setNotice(jsonObject.getString(""));
        baseInfoPO.setDescription(jsonObject.getString(""));
        baseInfoPO.setCustom_url_name(jsonObject.getString(""));
        baseInfoPO.setCustom_url(jsonObject.getString(""));
        baseInfoPO.setCustom_url_sub_title(jsonObject.getString(""));
        baseInfoPO.setPromotion_url(jsonObject.getString(""));
        baseInfoPO.setPromotion_url_name(jsonObject.getString(""));

        memberCardPO.setPrerogative(jsonObject.getString("prerogative"));
        CustomCell1PO customCell1PO = new CustomCell1PO();


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
