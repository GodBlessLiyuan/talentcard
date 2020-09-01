package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.EventEnrollDTO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-01 10:24
 * @description
 */
public interface IEventService {
    /**
     * 报名
     * @param eventEnrollDTO
     * @return
     */
    ResultVO enroll(EventEnrollDTO eventEnrollDTO);

    /**
     * 取消报名
     * @param etId
     * @return
     */
    ResultVO cancel(Long etId);

    /**
     * 检查
     * @param eventEnrollDTO
     * @return
     */
    ResultVO check(EventEnrollDTO eventEnrollDTO);

    /**
     * 全部活动查询
     * @return
     */
    ResultVO findAllEvent(String openId);

    /**
     * 全部活动查询
     * @return
     */
    ResultVO findMyEvent(String openId);

    /**
     * 详情查询
     * @return
     */
    ResultVO findOne(Long eventId);
}
