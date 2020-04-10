package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ISmsService;
import com.talentcard.front.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private static HashMap<String, String> verifyCodeMap = new HashMap<>();
    private static HashMap<String, Long> verifyCodeTime = new HashMap<>();
    @Autowired
    private ITalentService iTalentService;
    @Autowired
    private ISmsService iSmsService;

    /**
     * 用户注册模块
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("register")
    public ResultVO register(@RequestBody JSONObject jsonObject) {
        //判断验证码
        Long verifyCreateTime = verifyCodeTime.get(jsonObject.getString("phone"));
        String verifyCode = verifyCodeMap.get(jsonObject.getString("phone"));
        System.out.println(verifyCreateTime);
        System.out.println(verifyCode);
        if ((System.currentTimeMillis() - verifyCreateTime) >= 300000) {
            //验证码超时
            return new ResultVO(2301);
        } else if (!verifyCode.equals(jsonObject.getString("verifyCode"))) {
            //验证码错误
            return new ResultVO(2302);
        }
        return iTalentService.register(jsonObject);
    }


    /**
     * 用户输入手机号后，向其发送短信验证码
     */
    @PostMapping("sms")
    public ResultVO sms(@RequestParam String phone) {
        Long verifyCreateTime = verifyCodeTime.get(phone);
        //60内只能发一次验证码
        if (verifyCreateTime != null) {
            if ((System.currentTimeMillis() - verifyCreateTime) <= 60000) {
                return new ResultVO(2300);
            }
        }
        //6位短信验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //调用第三方短信接口，给指定手机号码发送指定短信验证码
        if (iSmsService.sendSMS(phone, verifyCode) == 1) {
            //短信发送成功
            verifyCodeMap.put(phone, verifyCode);
            verifyCodeTime.put(phone, System.currentTimeMillis());
            return new ResultVO(1000);
        } else {
            //短信发送失败
            return new ResultVO<>(2000);
        }
    }

}
