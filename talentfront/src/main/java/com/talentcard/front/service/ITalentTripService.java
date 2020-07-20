package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.vo.TripAvailableVO;

import java.text.ParseException;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:25
 * @description
 */
public interface ITalentTripService {
    /**
     * 根据openId查找当前用户所拥有特权的二级目录有哪些
     *
     * @param openId
     * @return
     */
    ResultVO findSecondContent(String openId, String name, Byte starLevel, Byte area, Byte order);

    /**
     * 根据activitySecondContentId查找景区detail
     *
     * @param activitySecondContentId
     * @return
     */
    ResultVO findOne(Long activitySecondContentId, String openId);


    /**
     * 领取景区福利
     *
     * @param openId
     * @param activitySecondContentId
     * @return
     */
    ResultVO getBenefit(String openId, Long activitySecondContentId) throws ParseException;

    /**
     * 得到当前人才某个景区福利的剩余次数
     *
     * @param openId
     * @param activitySecondContentId
     * @return
     */
    ResultVO getResidueTimes(String openId, Long activitySecondContentId);

    TripAvailableVO getTalentTripAllNum(String openId);
}
