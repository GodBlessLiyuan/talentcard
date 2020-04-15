package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ISmsService;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.utils.AccessTokenUtil;
import com.talentcard.front.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Random;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/10 09:28
 * @description 人才模块：注册，认证等
 */
@RequestMapping("talent")
@RestController
public class TalentController {
    @Autowired
    private ITalentService iTalentService;
    @Autowired
    private ISmsService iSmsService;

    /**
     * 用户一次打开，判断当前用户状态
     * null，则表示没使用过，其余取决于status
     * 1是已认证；2是未认证；3是认证中
     *
     * @param openId
     * @return
     */
    @PostMapping("findStatus")
    public ResultVO<TalentPO> findStatus(@RequestParam String openId) {
        return iTalentService.findStatus(openId);
    }

    /**
     * 用户注册模块
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("register")
    public ResultVO register(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        //判断验证码
        String verificationCode = VerificationCodeUtil.getCode(phone);
        if (verificationCode == null || verificationCode == "") {
            //查不到验证码
            return new ResultVO(2302);
        }
        if (!verificationCode.equals(jsonObject.getString("verifyCode"))) {
            //验证码错误
            return new ResultVO(2301);
        }
        return iTalentService.register(jsonObject);
    }


    /**
     * 用户输入手机号后，向其发送短信验证码
     */
    @PostMapping("sms")
    public ResultVO sms(@RequestParam String phone) {
        //6位短信验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //调用第三方短信接口，给指定手机号码发送指定短信验证码
        if (iSmsService.sendSMS(phone, verifyCode) == 1) {
            //短信发送成功，则将验证码存入缓存
            VerificationCodeUtil.setCode(phone, verifyCode);
            return new ResultVO(1000);
        } else {
            //短信发送失败
            return new ResultVO<>(2303);
        }
    }

    /**
     * 回填信息
     *
     * @param openId
     * @return
     */
    @PostMapping("findRegisterOne")
    public ResultVO findRegisterOne(@RequestParam(value = "openId") String openId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("openId", openId);
        return iTalentService.findRegisterOne(openId);
    }

    /**
     * 返回信息
     *
     * @param openId
     * @param status 1：已同意使用中；2：已驳回；3：注册中 4：待审批；5废弃
     * @return
     */
    @PostMapping("findOne")
    public ResultVO findOne(@RequestParam(value = "openId") String openId,
                            @RequestParam(value = "status") String status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("openId", openId);
        hashMap.put("status", status);
        return iTalentService.findOne(hashMap);
    }

    /**
     * 用户认证模块
     *
     * @param openId
     * @param political
     * @param education
     * @param school
     * @param firstClass
     * @param major
     * @param profQualityCategory
     * @param profQualityInfo
     * @param profTitleCategory
     * @param profTitleInfo
     * @param educPicture
     * @param profTitlePicture
     * @param profQualityPicture
     * @return
     */
    @PostMapping("identification")
    public ResultVO identification(@RequestParam(value = "openId") String openId,
                                   @RequestParam(value = "political") Byte political,
                                   @RequestParam(value = "education") Integer education,
                                   @RequestParam(value = "school") String school,
                                   @RequestParam(value = "firstClass") Byte firstClass,
                                   @RequestParam(value = "major") String major,
                                   @RequestParam(value = "profQualityCategory") Integer profQualityCategory,
                                   @RequestParam(value = "profQualityInfo") String profQualityInfo,
                                   @RequestParam(value = "profTitleCategory") Integer profTitleCategory,
                                   @RequestParam(value = "profTitleInfo") String profTitleInfo,
                                   @RequestParam(value = "educPicture") MultipartFile educPicture,
                                   @RequestParam(value = "profTitlePicture") MultipartFile profTitlePicture,
                                   @RequestParam(value = "profQualityPicture") MultipartFile profQualityPicture) {
        return iTalentService.identification(openId, political, education, school, firstClass,
                major, profQualityCategory, profQualityInfo, profTitleCategory, profTitleInfo,
                educPicture, profTitlePicture, profQualityPicture);
    }

    /**
     * 第一次申请认证后激活卡套
     *
     * @param openId
     * @param code
     * @return
     */
    @PostMapping("activate")
    public ResultVO activate(@RequestParam(value = "openId") String openId,
                             @RequestParam(value = "code") String code) {
        return iTalentService.activate(openId, code);
    }

}
