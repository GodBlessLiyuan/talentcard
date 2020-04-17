package com.talentcard.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.wechat.create.BaseInfoPO;
import com.talentcard.common.pojo.wechat.create.CustomCell1PO;
import com.talentcard.common.pojo.wechat.create.MemberCardPO;
import com.talentcard.common.pojo.wechat.create.WxCardPO;
import com.talentcard.common.utils.WechatApiUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class CardUtil {
    public static JSONObject addCommonCard(String brandName, String title, String notice,
                                           String description, String prerogative,
                                           String background) {
        /**
         * 1. 实体类或集合转JSON串
         * String jsonString = JSONObject.toJSONString(实体类);
         * 2.JSON串转JSONObject
         * JSONObject jsonObject = JSONObject.parseObject(jsonString);
         * setBackground_pic_url; setLogo_url;
         *
         */
        WxCardPO wxCardPO = new WxCardPO();

        MemberCardPO memberCardPO = new MemberCardPO();

        memberCardPO.setBackground_pic_url(background);
        BaseInfoPO baseInfoPO = new BaseInfoPO();
        baseInfoPO.setLogo_url("http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZ/0");
        baseInfoPO.setBrand_name(brandName);
        baseInfoPO.setCode_type("CODE_TYPE_NONE");
        baseInfoPO.setTitle(title);
        baseInfoPO.setColor("Color070");
        baseInfoPO.setNotice(notice);
        baseInfoPO.setDescription(description);
        baseInfoPO.setCenter_title("我是center");
        baseInfoPO.setCenter_sub_title("centerT");
        baseInfoPO.setCenter_url("www.baidu.com");
        baseInfoPO.setCustom_url_name("我是custom");
        baseInfoPO.setCustom_url("www.baidu.com");
        baseInfoPO.setCustom_url_sub_title("我是customTitle");
        baseInfoPO.setPromotion_url_name("我是pUrlName");
        baseInfoPO.setPromotion_url("www.sohu.com");

        memberCardPO.setBase_info(baseInfoPO);
        memberCardPO.setPrerogative(prerogative);

        CustomCell1PO customCell1PO = new CustomCell1PO();
        customCell1PO.setName("CellName");
        customCell1PO.setTips("CellTips");
        customCell1PO.setUrl("www.sina.com.cn");
        memberCardPO.setCustom_cell1(customCell1PO);
        wxCardPO.setMember_card(memberCardPO);
        JSONObject cardObject = new JSONObject();
        cardObject.put("card", wxCardPO);

        //发送post请求，激活卡套
        String url = "https://api.weixin.qq.com/card/create?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.postRequest(url, cardObject);
//        return result;
        return cardObject;
    }


    public static JSONObject addSeniorCard(String brandName, String title, String notice,
                                           String description, String prerogative,
                                           String background) {
        WxCardPO wxCardPO = new WxCardPO();

        MemberCardPO memberCardPO = new MemberCardPO();

        memberCardPO.setBackground_pic_url(background);
        BaseInfoPO baseInfoPO = new BaseInfoPO();
        baseInfoPO.setLogo_url("http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZ/0");
        baseInfoPO.setBrand_name(brandName);
        baseInfoPO.setCode_type("CODE_TYPE_NONE");
        baseInfoPO.setTitle(title);
        baseInfoPO.setColor("Color070");
        baseInfoPO.setNotice(notice);
        baseInfoPO.setDescription(description);
        baseInfoPO.setCenter_title("我是center");
        baseInfoPO.setCenter_sub_title("centerT");
        baseInfoPO.setCenter_url("www.baidu.com");
        baseInfoPO.setCustom_url_name("我是custom");
        baseInfoPO.setCustom_url("www.baidu.com");
        baseInfoPO.setCustom_url_sub_title("我是customTitle");
        baseInfoPO.setPromotion_url_name("我是pUrlName");
        baseInfoPO.setPromotion_url("www.sohu.com");

        memberCardPO.setBase_info(baseInfoPO);
        memberCardPO.setPrerogative(prerogative);

        CustomCell1PO customCell1PO = new CustomCell1PO();
        customCell1PO.setName("CellName");
        customCell1PO.setTips("CellTips");
        customCell1PO.setUrl("www.sina.com.cn");
        memberCardPO.setCustom_cell1(customCell1PO);
        wxCardPO.setMember_card(memberCardPO);
        JSONObject cardObject = new JSONObject();
        cardObject.put("card", wxCardPO);

        //发送post请求，激活卡套
        String url = "https://api.weixin.qq.com/card/create?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.postRequest(url, cardObject);
        return result;
    }

    /**
     * 把微信接口需要的图片上传到对应CDN上
     *
     * @param
     * @return
     */
    public static String uploadPicture(String picture) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
                + AccessTokenUtil.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(picture));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("buffer", resource);
        JSONObject jsonObject = restTemplate.postForObject(url, param, JSONObject.class);
        return jsonObject.getString("url");
    }

}
