package com.talentcard.front.controller;

import com.netflix.discovery.converters.Auto;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 20:07
 * @description
 */
@RequestMapping("talentActivity")
@RestController
public class TalentActivityController {
    @Autowired
    private ITalentActivityService iTalentActivityService;

    /**
     * 根据openId查找当前用户所拥有特权的一级目录有哪些
     *
     * @param openId
     * @return
     */
    @RequestMapping("findFirstContent")
    public ResultVO findFirstContent(@RequestParam("openId") String openId) {
        return iTalentActivityService.findFirstContent(openId);
    }

}
