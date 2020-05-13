package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 20:04
 * @description
 */
public interface ITalentActivityService{
    /**
     * 根据openId查找当前用户所拥有特权的一级目录有哪些
     * @param openId
     * @return
     */
    ResultVO findFirstContent(String openId);

    /**
     * 根据openId查找当前用户所参加活动的历史记录
     * @param openId
     * @return
     */
    ResultVO findHistory(String openId);

    /**
     * 用卡号拿到人才openId
     * @param cardNum
     * @return
     */
    String getOpenId(String cardNum);

}
