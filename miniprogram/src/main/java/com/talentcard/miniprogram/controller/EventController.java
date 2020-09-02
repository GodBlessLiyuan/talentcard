package com.talentcard.miniprogram.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.EventEnrollDTO;
import com.talentcard.miniprogram.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-01 10:20
 * @description
 */
@RestController
@RequestMapping("event")
public class EventController {
    @Autowired
    IEventService iEventService;

    /**
     * 报名
     *
     * @param eventEnrollDTO
     * @return
     */
    @RequestMapping("enroll")
    public ResultVO enroll(@RequestBody EventEnrollDTO eventEnrollDTO) {
        return iEventService.enroll(eventEnrollDTO);
    }

    /**
     * 取消报名
     *
     * @param etId
     * @return
     */
    @RequestMapping("cancel")
    public ResultVO cancel(@RequestParam("etId") Long etId) {
        return iEventService.cancel(etId);
    }


    /**
     * 检测是否符合条件
     *
     * @param eventEnrollDTO
     * @return
     */
    @RequestMapping("check")
    public ResultVO check(@RequestBody EventEnrollDTO eventEnrollDTO) {
        return iEventService.check(eventEnrollDTO);
    }

    /**
     * 全部活动查询
     *
     * @return
     */
    @RequestMapping("findAllEvent")
    public ResultVO findAllEvent(@RequestParam("openId") String openId) {
        return iEventService.findAllEvent(openId);
    }

    /**
     * 我的活动查询
     *
     * @return
     */
    @RequestMapping("findMyEvent")
    public ResultVO findMyEvent(@RequestParam("openId") String openId) {
        return iEventService.findMyEvent(openId);
    }

    /**
     * 活动详情查询
     *
     * @return
     */
    @RequestMapping("findOne")
    public ResultVO findOne(@RequestParam("openId") String openId,
                            @RequestParam("eventId") Long eventId) {
        return iEventService.findOne(openId, eventId);
    }
}
