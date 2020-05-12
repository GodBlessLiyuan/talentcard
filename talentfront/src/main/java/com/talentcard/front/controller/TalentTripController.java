package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentActivityService;
import com.talentcard.front.service.ITalentTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 09:57
 * @description
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

}