package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
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
     * @description 用户首次注册
     * @param jsonObject
     * @return ResultVO，带信息码
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
            userPO.setUpdateTime(jsonObject.getDate("updateTime"));
            iUserService.insert(userPO);

            AuthenticationPO authenticationPO = new AuthenticationPO();
            authenticationPO.setUserId(userPO.getUserId());
//            authenticationPO.setPoliticalStatus(jsonObject.getByte("politicalStatus"));
            authenticationPO.setEducationBackground(jsonObject.getByte("educationBackground"));
            authenticationPO.setSchool(jsonObject.getString("School"));
            authenticationPO.setFamousSchool(jsonObject.getByte("famousSchool"));
            authenticationPO.setMajor(jsonObject.getString("major"));
//            authenticationPO.setEducationCertification(jsonObject.getString("educationCertification"));
            authenticationPO.setProfessionalTitle(jsonObject.getString("professionalTitle"));
            authenticationPO.setProfessionalTitleInfo(jsonObject.getString("professionalTitleInfo"));
            authenticationPO.setProfessionalQualification(jsonObject.getString("professionalQualification"));
            authenticationPO.setProfessionalQualificationInfo(jsonObject.getString("professionalQualificationInfo"));
            authenticationPO.setStatus((byte) 0);
            authenticationPO.setCreateTime(new Date());
            authenticationPO.setUpdateTime(new Date());
            iAuthenticationService.insert(authenticationPO);
            return new ResultVO(1000, "");
    }
}
