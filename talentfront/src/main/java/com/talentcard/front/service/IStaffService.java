package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:40
 * @description
 */
public interface IStaffService {
    ResultVO findStaff(String openId);
}
