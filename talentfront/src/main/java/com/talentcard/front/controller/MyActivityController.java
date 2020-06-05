package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IMyActivityService;
import com.talentcard.front.utils.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-04 14:26
 * @description
 */
@RequestMapping("myActivity")
@RestController
public class MyActivityController {
    @Autowired
    private IMyActivityService iMyActivityService;

    /**
     * 添加意见反馈
     *
     * @param openId
     * @param content
     * @param file
     * @param contact
     * @return
     */
    @PostMapping("addFeedBack")
    public ResultVO addFeedBack(@RequestParam(value = "openId") String openId,
                                @RequestParam(value = "content") String content,
                                @RequestParam(value = "picture", required = false) MultipartFile file,
                                @RequestParam(value = "contact", required = false, defaultValue = "") String contact) {

        return iMyActivityService.addFeedBack(openId, content, file, contact);
    }


    /**
     *我的足迹接口
     * @param openId
     * @return
     */
    @PostMapping("footprint")
    public ResultVO footprint(@RequestParam(value = "openId") String openId) {

        return iMyActivityService.footprint(openId);
    }

}
