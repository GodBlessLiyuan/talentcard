package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITripStaffService;
import com.talentcard.front.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 09:04
 * @description 旅游员工
 */
@RequestMapping("tripStaff")
@RestController
/**
 *
 */
public class TripStaffController {
    @Autowired
    private ITripStaffService iTripStaffService;

    /**
     * 注册
     * @param jsonObject
     * @return
     */
    @PostMapping("register")
    public ResultVO register(@RequestBody JSONObject jsonObject) {
        return iTripStaffService.register(jsonObject);
    }

    /**
     * 判断当前用户是否注册
     * 以及是否能够注册
     * @return
     */
    @RequestMapping("ifEnableRegister")
    public ResultVO ifEnableRegister(String openId, Long scenicId) {
        return new ResultVO(1000);
    }


}
