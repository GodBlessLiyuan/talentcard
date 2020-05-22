package com.talentcard.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.WechatApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-07 09:56
 * @description
 */
@Component
public class TalentInfoUpdateUtil {
    @Autowired
    private CertificationMapper cMapper;
    @Autowired
    private EducationMapper eMapper;
    @Autowired
    private ProfTitleMapper ptMapper;
    @Autowired
    private ProfQualityMapper pqMapper;
    @Autowired
    private TalentMapper tMapper;
    @Autowired
    private TalentHonourMapper thMapper;

    private static CertificationMapper certificationMapper;
    private static EducationMapper educationMapper;
    private static ProfTitleMapper profTitleMapper;
    private static ProfQualityMapper profQualityMapper;
    private static TalentMapper talentMapper;
    private static TalentHonourMapper talentHonourMapper;


    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void mapperInitialize() {
        certificationMapper = cMapper;
        educationMapper = eMapper;
        profTitleMapper = ptMapper;
        profQualityMapper = pqMapper;
        talentMapper = tMapper;
        talentHonourMapper = thMapper;
    }

    /**
     * 根据certId，更新4表对应状态
     *
     * @param certId
     * @param status
     */
    public static void fiveTableUpdate(Long certId, Byte status) {
        certificationMapper.updateStatusByCertId(certId, status);
        educationMapper.updateStatusByCertId(certId, status);
        profTitleMapper.updateStatusByCertId(certId, status);
        profQualityMapper.updateStatusByCertId(certId, status);
        talentHonourMapper.updateStatusByCertId(certId, status);
    }

    public static void updateJuniorCardCustomField(String code, String wxCardId, Long talentId) {
        String cardHolderUrl = "https://api.weixin.qq.com/card/membercard/updateuser?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject updateMemberInfo = new JSONObject();
        updateMemberInfo.put("code", code);
        updateMemberInfo.put("card_id", wxCardId);
        //持卡人姓名
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        String cardHolderName = talentPO.getName();
        if (cardHolderName.length() > 4) {
            cardHolderName = cardHolderName.substring(0, 4);
        }
        updateMemberInfo.put("custom_field_value1", cardHolderName);
        WechatApiUtil.postRequest(cardHolderUrl, updateMemberInfo);
    }

    public static void updateSeniorCardCustomField(String code, String wxCardId, Long talentId) {
        String cardHolderUrl = "https://api.weixin.qq.com/card/membercard/updateuser?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject updateMemberInfo = new JSONObject();
        updateMemberInfo.put("code", code);
        updateMemberInfo.put("card_id", wxCardId);
        //持卡人姓名
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        String cardHolderName = talentPO.getName();
        if (cardHolderName.length() > 4) {
            cardHolderName = cardHolderName.substring(0, 4);
        }
        //性别
        String gender;
        if (talentPO.getSex() == 1) {
            gender = "男";
        } else {
            gender = "女";
        }
        updateMemberInfo.put("custom_field_value1", cardHolderName);
        updateMemberInfo.put("custom_field_value2", gender);
        updateMemberInfo.put("custom_field_value3", "立即使用");
        WechatApiUtil.postRequest(cardHolderUrl, updateMemberInfo);
    }
}
