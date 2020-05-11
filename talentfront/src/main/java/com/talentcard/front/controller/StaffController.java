package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:51
 * @description 员工
 */
@RequestMapping("staff")
@RestController
public class StaffController {
    @Autowired
    private IStaffService iStaffService;

    /**
     * 判断当前员工是否注册
     * 以及是否能够注册
     *
     * @return
     */
    @RequestMapping("ifEnableRegister")
    public ResultVO ifEnableRegister(@RequestParam(value = "openId") String openId,
                                     @RequestParam(value = "activityFirstContent") Long activityFirstContent,
                                     @RequestParam(value = "activitySecondContent") Long activitySecondContent) {
        return iStaffService.ifEnableRegister(openId, activityFirstContent, activitySecondContent);
    }

    /**
     * 注册
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("register")
    public ResultVO register(@RequestBody JSONObject jsonObject) {
        return iStaffService.register(jsonObject);
    }

    /**
     * 根据openId，企业服务里查找已经存在的员工
     * 返回：活动景区名称、员工姓名等信息
     *
     * @param openId
     * @return
     */
    @RequestMapping("findStaffBusinessService")
    public ResultVO findStaffBusinessService(String openId) {
        return iStaffService.findStaffBusinessService(openId);
    }
}
