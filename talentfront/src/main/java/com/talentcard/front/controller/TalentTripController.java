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
     * @param openId
     * @param name
     * @param starlevel
     * @param area
     * @param order
     * @return
     */
    @RequestMapping("findSecondContent")
    public ResultVO findSecondContent(@RequestParam(value = "openId") String openId,
                                      @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                      @RequestParam(value = "starlevel", required = false) Byte starlevel,
                                      @RequestParam(value = "area", required = false) Byte area,
                                      @RequestParam(value = "order", required = false, defaultValue = "2") Byte order) {
        return iTalentTripService.findSecondContent(openId, name, starlevel, area, order);
    }

    /**
     * 根据scenicId查找景区detail
     *
     * @param activitySecondContentId
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("activitySecondContentId") Long activitySecondContentId,
                            @RequestParam(value = "openId", required = false) String openId) {
        return iTalentTripService.findOne(activitySecondContentId, openId);
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