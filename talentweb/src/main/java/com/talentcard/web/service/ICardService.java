package com.talentcard.web.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/13 19:00
 * @Description: 卡用的sevice层接口
 */
public interface ICardService {
    ResultVO add(JSONObject jsonObject);
}
