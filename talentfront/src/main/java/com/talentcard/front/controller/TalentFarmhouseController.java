package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentFarmhouseService;
import com.talentcard.front.service.ITalentTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-13 18:26
 * @description
 */
@RequestMapping("talentFarmhouse")
@RestController
public class TalentFarmhouseController {
    @Autowired
    private ITalentFarmhouseService iTalentFarmhouseService;

    /**
     * 根据openId查找当前用户所拥有特权的二级目录有哪些
     *
     * @param openId
     * @return
     */
    @RequestMapping("findSecondContent")
    public ResultVO findSecondContent(@RequestParam("openId") String openId) {
        return iTalentFarmhouseService.findSecondContent(openId);
    }

    /**
     * 根据scenicId查找景区detail
     *
     * @param activitySecondContentId
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("activitySecondContentId") Long activitySecondContentId) {
        return iTalentFarmhouseService.findOne(activitySecondContentId);
    }

}