package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 14:49
 * @description
 */
public interface ITalentEventService {
    /**
     * 总活动查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @param type
     * @param status
     * @return
     */
    ResultVO query(Integer pageNum, Integer pageSize, String name, Byte type, Byte status);

    /**
     * 计算报名人数
     * @param eventId
     * @return
     */
    ResultVO countNum(Long eventId);
}
