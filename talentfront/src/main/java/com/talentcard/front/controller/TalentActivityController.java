package com.talentcard.front.controller;

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
 * @description 福利活动总的接口
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

    /**
     * 根据openId查找当前用户所参加活动的历史记录
     *
     * @param openId
     * @return
     */
    @RequestMapping("findHistory")
    public ResultVO findHistory(@RequestParam("openId") String openId) {
        return iTalentActivityService.findHistory(openId);
    }
}
