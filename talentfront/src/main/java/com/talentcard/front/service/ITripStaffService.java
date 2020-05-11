package com.talentcard.front.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 09:13
 * @description
 */
public interface ITripStaffService {
    /**
     * 旅游员工注册
     * @param jsonObject
     * @return
     */
    ResultVO register(JSONObject jsonObject);

    /**
     * 判断旅游员工状态
     * 1. 是否已经绑定景区/农家乐
     * 2. 是否达到绑定上限
     * @return
     */
    ResultVO ifEnableRegister(String openId, Long scenicId);
}
