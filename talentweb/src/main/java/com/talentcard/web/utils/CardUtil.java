package com.talentcard.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.wechat.create.BaseInfoPO;
import com.talentcard.common.pojo.wechat.create.CustomCell1PO;
import com.talentcard.common.pojo.wechat.create.MemberCardPO;
import com.talentcard.common.pojo.wechat.create.WxCardPO;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/17 14:33
 * @Description: 卡的创建接口
 */
@Component
public class CardUtil {
    /**
     * 基本卡
     * @param brandName
     * @param title
     * @param notice
     * @param description
     * @param prerogative
     * @param background
     * @param logoUrl
     * @return
     */
    public static JSONObject addCommonCard(String brandName, String title, String notice,
                                           String description, String prerogative,
                                           String background, String logoUrl) {
        WxCardPO wxCardPO = new WxCardPO();

        MemberCardPO memberCardPO = new MemberCardPO();

        memberCardPO.setBackground_pic_url(background);
        BaseInfoPO baseInfoPO = new BaseInfoPO();
        baseInfoPO.setLogo_url(logoUrl);
        baseInfoPO.setBrand_name(brandName);
        baseInfoPO.setCode_type("CODE_TYPE_TEXT");
        baseInfoPO.setTitle(title);
        baseInfoPO.setColor("Color030");
        baseInfoPO.setNotice(notice);
        baseInfoPO.setDescription(description);
        //立即认证
        baseInfoPO.setCenter_title("立即认证");
        baseInfoPO.setCenter_sub_title("");
        baseInfoPO.setCenter_url(WebParameterUtil.getCertificateUrl());
        //我的信息
        baseInfoPO.setCustom_url_name("我的信息");
        baseInfoPO.setCustom_url(WebParameterUtil.getMyInfoNotCertificateUrl());
        baseInfoPO.setCustom_url_sub_title("");
        //我的权益
        baseInfoPO.setPromotion_url_name("我的权益");
        baseInfoPO.setPromotion_url(WebParameterUtil.getMyBaseRightUrl());

        memberCardPO.setBase_info(baseInfoPO);
        memberCardPO.setPrerogative(prerogative);

        //基础卡没有我的申请
//        CustomCell1PO customCell1PO = new CustomCell1PO();
//        customCell1PO.setName("我的申请");
//        customCell1PO.setTips("");
//        customCell1PO.setUrl("http://dev.localcards.gov.vbooster.cn/wx/#my_apply");
//        memberCardPO.setCustom_cell1(customCell1PO);
        wxCardPO.setMember_card(memberCardPO);
        JSONObject cardObject = new JSONObject();
        cardObject.put("card", wxCardPO);

        //发送post请求，激活卡套
        String url = "https://api.weixin.qq.com/card/create?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.postRequest(url, cardObject);
        return result;
//        return cardObject;
    }

    /**
     * 高级卡
     *
     * @param brandName
     * @param title
     * @param notice
     * @param description
     * @param prerogative
     * @param background
     * @param logoUrl
     * @return
     */
    public static JSONObject addSeniorCard(String brandName, String title, String notice,
                                           String description, String prerogative,
                                           String background, String logoUrl) {
        WxCardPO wxCardPO = new WxCardPO();

        MemberCardPO memberCardPO = new MemberCardPO();

        memberCardPO.setBackground_pic_url(background);
        BaseInfoPO baseInfoPO = new BaseInfoPO();
        baseInfoPO.setLogo_url(logoUrl);
        baseInfoPO.setBrand_name(brandName);
        baseInfoPO.setCode_type("CODE_TYPE_QRCODE");
        baseInfoPO.setTitle(title);
        baseInfoPO.setColor("Color070");
        baseInfoPO.setNotice(notice);
        baseInfoPO.setDescription(description);
        //高级卡没有立即认证
//        baseInfoPO.setCenter_title("立即认证");
//        baseInfoPO.setCenter_sub_title("");
//        baseInfoPO.setCenter_url("http://dev.localcards.gov.vbooster.cn/wx/#not_certified");
        //我的信息
        baseInfoPO.setCustom_url_name("我的信息");
        baseInfoPO.setCustom_url(WebParameterUtil.getMyInfoAlreadyCertificateUrl());
        baseInfoPO.setCustom_url_sub_title("");
        //我的权益
        baseInfoPO.setPromotion_url_name("我的权益");
        baseInfoPO.setPromotion_url(WebParameterUtil.getMySeniorRightUrl());

        memberCardPO.setBase_info(baseInfoPO);
        memberCardPO.setPrerogative(prerogative);

        //我的申请
        CustomCell1PO customCell1PO = new CustomCell1PO();
        customCell1PO.setName("我的申请");
        customCell1PO.setTips("");
        customCell1PO.setUrl(WebParameterUtil.getMyApplicationUrl());

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
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
        String access_token = AccessTokenUtil.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(picture));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("buffer", resource);
        params.add("access_token", access_token);
        String picUrl = restTemplate.postForObject(url, params, String.class);
        return picUrl;
    }

}
