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
}
