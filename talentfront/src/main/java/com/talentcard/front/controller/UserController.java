package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.UserBO;
import com.talentcard.common.pojo.AuthenticationPO;
import com.talentcard.common.pojo.UserPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IAuthenticationService;
import com.talentcard.front.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/24 14:47
 * @description 人才卡前端用户注册 模块
 */

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IAuthenticationService iAuthenticationService;

    /**
     * @param jsonObject
     * @return ResultVO，带信息码
     * @description 用户首次注册
     */
    @ResponseBody
    @PostMapping(value = "register")
    public ResultVO<Object> register(@RequestBody JSONObject jsonObject) {
        UserPO userPO = new UserPO();
        userPO.setWechatId(jsonObject.getString("wechatId"));
        userPO.setName(jsonObject.getString("name"));
        userPO.setIdentityNumber(jsonObject.getString("identityNumber"));
        userPO.setPassportId(jsonObject.getString("passportId"));
        userPO.setIndustry(jsonObject.getShort("industry"));
        userPO.setWorkUnit(jsonObject.getString("workUnit"));
        userPO.setPhone(jsonObject.getString("phone"));
        userPO.setGender(jsonObject.getByte("gender"));
        userPO.setCreateTime(new Date());
        userPO.setUpdateTime(new Date());
        userPO.setDr((byte)1);
        iUserService.insert(userPO);

        AuthenticationPO authenticationPO = new AuthenticationPO();
        authenticationPO.setUserId(userPO.getUserId());
        authenticationPO.setEducationBackground(jsonObject.getByte("educationBackground"));
        authenticationPO.setSchool(jsonObject.getString("School"));
        authenticationPO.setFamousSchool(jsonObject.getByte("famousSchool"));
        authenticationPO.setMajor(jsonObject.getString("major"));
        authenticationPO.setProfessionalTitle(jsonObject.getString("professionalTitle"));
        authenticationPO.setProfessionalTitleInfo(jsonObject.getString("professionalTitleInfo"));
        authenticationPO.setProfessionalQualification(jsonObject.getString("professionalQualification"));
        authenticationPO.setProfessionalQualificationInfo(jsonObject.getString("professionalQualificationInfo"));
        authenticationPO.setStatus((byte) 0);
        authenticationPO.setCreateTime(new Date());
        authenticationPO.setUpdateTime(new Date());
        authenticationPO.setDr((byte) 1);
        iAuthenticationService.insert(authenticationPO);
        return new ResultVO(1000, "");
    }


    /**
     * @param jsonObject
     * @return ResultVO，带信息码
     * @description 用户发起审批，status改为1审批中
     */
    //todo io传文件
    @ResponseBody
    @PostMapping(value = "authentication")
    public ResultVO<Object> authentication(@RequestBody JSONObject jsonObject) {
        AuthenticationPO authenticationPO = new AuthenticationPO();
        authenticationPO.setUserId(jsonObject.getLong("userId"));
        authenticationPO.setPoliticalStatus(jsonObject.getByte("politicalStatus"));
        authenticationPO.setEducationBackground(jsonObject.getByte("educationBackground"));
        authenticationPO.setSchool(jsonObject.getString("School"));
        authenticationPO.setFamousSchool(jsonObject.getByte("famousSchool"));
        authenticationPO.setMajor(jsonObject.getString("major"));
        authenticationPO.setEducationCertification(jsonObject.getString("educationCertification"));
        authenticationPO.setProfessionalTitle(jsonObject.getString("professionalTitle"));
        authenticationPO.setProfessionalTitleInfo(jsonObject.getString("professionalTitleInfo"));
        authenticationPO.setProfessionalQualification(jsonObject.getString("professionalQualification"));
        authenticationPO.setProfessionalQualificationInfo(jsonObject.getString("professionalQualificationInfo"));
        authenticationPO.setStatus((byte) 1);
        authenticationPO.setCreateTime(new Date());
        authenticationPO.setUpdateTime(new Date());
        iAuthenticationService.insert(authenticationPO);
        return new ResultVO(1000, "");
    }

    /**
     * 通过微信id和状态值查找user信息，关联认证信息
     *
     * @param wechatId
     * @param status
     * @return
     */
    @ResponseBody
    @PostMapping(value = "findByWechat")
    public ResultVO<UserBO> findByWechat(@RequestParam(value = "wechatId", defaultValue = "") String wechatId,
                                         @RequestParam(value = "status", defaultValue = "1") Byte status) {

        return new ResultVO<>(1000, iUserService.findByWechat(wechatId, status));
    }
}
