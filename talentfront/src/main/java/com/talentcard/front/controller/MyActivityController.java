package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IMyActivityService;
import com.talentcard.front.service.ITalentFarmhouseService;
import com.talentcard.front.service.ITalentTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

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
    @Autowired
    private ITalentTripService iTalentTripService;
    @Autowired
    private ITalentFarmhouseService iTalentFarmhouseService;

    /**
     * 添加意见反馈
     *
     * @param openId
     * @param content
     * @param picture
     * @param contact
     * @return
     */
    @PostMapping("addFeedBack")
    public ResultVO addFeedBack(@RequestParam(value = "openId") String openId,
                                @RequestParam(value = "content") String content,
                                @RequestParam(value = "picture", required = false, defaultValue = "") String picture,
                                @RequestParam(value = "contact", required = false, defaultValue = "") String contact) {

        return iMyActivityService.addFeedBack(openId, content, picture, contact);
    }


    /**
     * 我的足迹接口
     *
     * @param openId
     * @return
     */
    @PostMapping("footprint")
    public ResultVO footprint(@RequestParam(value = "openId") String openId) {
        return iMyActivityService.footprint(openId);
    }

    /**
     * 我的收藏设定
     *
     * @param openId
     * @return
     */
    @PostMapping("collect")
    public ResultVO collect(@RequestParam(value = "openId") String openId,
                            @RequestParam(value = "activityFirstContentId") Long activityFirstContentId,
                            @RequestParam(value = "activitySecondContentId") Long activitySecondContentId,
                            @RequestParam(value = "ifCollect") Byte ifCollect) {
        return iMyActivityService.collect(openId, activityFirstContentId, activitySecondContentId, ifCollect);
    }

    /**
     * 我的收藏查询接口
     *
     * @param openId
     * @return
     */
    @PostMapping("findMyCollect")
    public ResultVO findMyCollect(@RequestParam(value = "openId") String openId) {
        return iMyActivityService.findMyCollect(openId);
    }

    /**
     * 全局搜索
     *
     * @param openId
     * @param name
     * @return
     */
    @PostMapping("queryOverall")
    public ResultVO queryOverall(@RequestParam(value = "openId") String openId,
                                 @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        ResultVO tripResultVO = iTalentTripService.findSecondContent(openId, name, null, null, (byte) 1);
        ResultVO farmhouseResultVO = iTalentFarmhouseService.findSecondContent(openId, name, null, (byte) 1);
        if (tripResultVO == null || farmhouseResultVO == null) {
            return new ResultVO(2510, "全局搜索resultVO返回空指针异常！");
        }
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> tripResult = (HashMap<String, Object>) tripResultVO.getData();
        HashMap<String, Object> farmhouseResult = (HashMap<String, Object>) farmhouseResultVO.getData();
        if (tripResult != null) {
            if (tripResult.get("scenicList") != null) {
                result.put("tripResult", tripResult.get("scenicList"));
            } else {
                result.put("tripResult", new ArrayList<>(0));
            }
        } else {
            result.put("tripResult", new ArrayList<>(0));
        }
        if (farmhouseResult != null) {
            if (farmhouseResult.get("farmhouseVOList") != null) {
                result.put("farmhouseResult", farmhouseResult.get("farmhouseVOList"));
            } else {
                result.put("farmhouseResult", new ArrayList<>(0));
            }
        }else {
            result.put("farmhouseResult", new ArrayList<>(0));
        }
        return new ResultVO(1000, result);
    }
}
