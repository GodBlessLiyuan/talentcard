package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:25
 * @description
 */
public interface ITalentTripService {
    /**
     * 根据openId查找当前用户所拥有特权的二级目录有哪些
     * @param openId
     * @return
     */
    ResultVO findSecondContent(String openId);

}
