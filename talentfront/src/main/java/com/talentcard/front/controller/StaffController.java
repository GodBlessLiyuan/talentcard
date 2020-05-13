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
 * @description 福利活动 员工总的接口
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
                                     @RequestParam(value = "activityFirstContentId") Long activityFirstContentId,
                                     @RequestParam(value = "activitySecondContentId") Long activitySecondContentId) {
        return iStaffService.ifEnableRegister(openId, activityFirstContentId, activitySecondContentId);
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
     * 根据openId，查找员工
     * 返回：活动景区名称、员工姓名等信息
     *
     * @param openId
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("openId") String openId) {
        return iStaffService.findOne(openId);
    }

    /**
     * 核销
     * 根据一级目录id，决定是否核销哪一个
     *
     * @return
     */
    @RequestMapping("vertify")
    public ResultVO vertify(@RequestParam(value = "talentOpenId") String talentOpenId,
                            @RequestParam(value = "staffOpenId") String staffOpenId,
                            @RequestParam(value = "activityFirstContentId") Long activityFirstContentId,
                            @RequestParam(value = "activitySecondContentId") Long activitySecondContentId) {
        ResultVO resultVO = null;
        /**
         * 1.旅游
         */
        if (activityFirstContentId == 1) {
            resultVO = iStaffService.tripVertify(talentOpenId, staffOpenId, activitySecondContentId);
        }
        return resultVO;
    }
}
