package com.talentcard.front.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;

import java.util.HashMap;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/10 09:10
 * @Description: 人才用的sevice层接口
 */
public interface ITalentService {
    /**
     * 注册
     * @param jsonObject
     * @return
     */
    ResultVO register(JSONObject jsonObject);

    ResultVO findOne(HashMap<String, Object> hashMap);
}
