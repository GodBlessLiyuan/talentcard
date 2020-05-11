package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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


    @RequestMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                          @RequestParam(value = "activitySecondContentName", required = false) String activitySecondContentName,
                          @RequestParam(value = "activityFirstContentId", required = false) Integer activityFirstContentId,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                          @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!startTime.equals("")) {
            startTime = startTime + " 00:00:00";
        }
        if (!endTime.equals("")) {
            endTime = endTime + " 23:59:59";
        }
        hashMap.put("activitySecondContentName", activitySecondContentName);
        hashMap.put("activityFirstContentId", activityFirstContentId);
        hashMap.put("name", name);
        hashMap.put("startTime", startTime);
        hashMap.put("endTime", endTime);
        return iStaffService.query(pageNum, pageSize, hashMap);
    }
}
