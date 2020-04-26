package com.talentcard.wechat.controller;

import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/20 14:17
 * @description 微信模块：人才的领卡和删除事件等
 */
@RequestMapping("event")
@RestController
public class EventController {
    @Autowired
    private IEventService iEventService;

    /**
     * 根据openId激活（业务层该表和核销旧卡）
     *
     * @param openId
     * @return
     */
    @PostMapping("activate")
    public ResultVO<TalentPO> findStatus(@RequestParam String openId) {
        //激活service层接口
        return iEventService.activate(openId);
    }

    /**
     * 用户删卡后触发
     *
     * @param openId
     * @return
     */
    @PostMapping("delete")
    public ResultVO<TalentPO> delete(@RequestParam String openId, @RequestParam String eventCardId) {
        //激活service层接口
        return iEventService.delete(openId, eventCardId);
    }
}
