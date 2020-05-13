package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 09:57
 * @description 福利活动 旅游部分的接口
 */
@RequestMapping("talentTrip")
@RestController
public class TalentTripController {
    @Autowired
    private ITalentTripService iTalentTripService;

    /**
     * 根据openId查找当前用户所拥有特权的二级目录有哪些
     *
     * @param openId
     * @return
     */
    @RequestMapping("findSecondContent")
    public ResultVO findSecondContent(@RequestParam("openId") String openId) {
        return iTalentTripService.findSecondContent(openId);
    }

    /**
     * 根据scenicId查找景区detail
     *
     * @param activitySecondContentId
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("activitySecondContentId") Long activitySecondContentId) {
        return iTalentTripService.findOne(activitySecondContentId);
    }

    /**
     * 获取福利
     *
     * @param openId
     * @param activitySecondContentId
     * @return
     */
    @RequestMapping("getBenefit")
    public ResultVO getBenefit(@RequestParam("openId") String openId,
                               @RequestParam("activitySecondContentId") Long activitySecondContentId) throws ParseException {
        return iTalentTripService.getBenefit(openId, activitySecondContentId);
    }

    /**
     * 得到当前人才某个景区福利的剩余次数
     *
     * @param openId
     * @param activitySecondContentId
     * @return
     */
    @RequestMapping("getResidueTimes")
    public ResultVO getResidueTimes(@RequestParam("openId") String openId,
                                    @RequestParam("activitySecondContentId") Long activitySecondContentId) {
        return iTalentTripService.getResidueTimes(openId, activitySecondContentId);
    }
}