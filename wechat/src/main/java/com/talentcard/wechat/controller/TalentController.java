package com.talentcard.wechat.controller;

import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.ICardActivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/20 14:17
 * @description 微信模块：人才的激活等
 */
@RequestMapping("talent")
@RestController
public class TalentController {
    @Autowired
    private ICardActivateService iCardActivateService;

    /**
     * 根据openId激活（业务层该表和核销旧卡）
     * @param openId
     * @return
     */
    @PostMapping("activate")
    public ResultVO<TalentPO> findStatus(@RequestParam String openId) {
        //激活service层接口
        return iCardActivateService.activate(openId);
    }
}
