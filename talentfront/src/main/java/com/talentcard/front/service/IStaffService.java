package com.talentcard.front.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:40
 * @description
 */
public interface IStaffService {
    /**
     * 判断旅游员工状态
     * 1. 是否已经绑定景区/农家乐
     * 2. 是否达到绑定上限
     *
     * @return
     */
    ResultVO ifEnableRegister(String openId, Long activityFirstContentId, Long activitySecondContentId);

    /**
     * 旅游员工注册
     *
     * @param jsonObject
     * @return
     */
    ResultVO register(JSONObject jsonObject);

    /**
     * 根据openId，企业服务里查找已经存在的员工
     * 返回：活动景区名称、员工姓名等信息
     *
     * @param openId
     * @return
     */
    ResultVO findOne(String openId);

    /**
     * @param talentOpenId
     * @param staffOpenId
     * @param activityFirstContentId
     * @param activitySecondContentId
     * @return
     */
    ResultVO vertify(String talentOpenId, String staffOpenId,
                     Long activityFirstContentId, Long activitySecondContentId);

    /**
     * 旅游核销
     * 判断是否有旅游资格
     * @param talentOpenId
     * @param staffOpenId
     * @param activitySecondContentId
     * @return
     */
    ResultVO tripVertify(String talentOpenId, String staffOpenId, Long activitySecondContentId);

    /**
     * 农家乐核销
     * 判断是否有农家乐资格
     * @param talentOpenId
     * @param staffOpenId
     * @param activitySecondContentId
     * @return
     */
    ResultVO farmhouseVertify(String talentOpenId, String staffOpenId, Long activitySecondContentId);
}
