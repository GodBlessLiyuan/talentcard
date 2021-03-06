package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.IdentificationDTO;
import com.talentcard.front.service.ISmsService;
import com.talentcard.front.service.ITalentService;
import com.talentcard.common.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (verificationCode == null || verificationCode.equals("")) {
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
     * 用户认证模块
     * @param identificationDTO
     * @return
     */
    @PostMapping("identification")
    public ResultVO identification(@RequestBody IdentificationDTO identificationDTO) {
        return iTalentService.identification(identificationDTO);
    }

    /**
     * 根据OpenId查找认证完成之前的基本信息
     * 或者认证之后的信息
     *
     * @param openId
     * @return
     */
    @PostMapping("findInfo")
    public ResultVO findInfo(@RequestParam(value = "openId") String openId) {
        return iTalentService.findInfo(openId);
    }

    /**
     * 填充UnionId
     */
    @RequestMapping("fillUnion")
    public ResultVO fullUnion() {
        return iTalentService.fillUnion();
    }


    @PostMapping("updateUnionId")
    public ResultVO updateUnionId(@RequestParam(value = "token") String token, @RequestParam(value = "openId") String openId) {
        return iTalentService.updateUnionId(token, openId);
    }
}
