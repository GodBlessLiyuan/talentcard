package com.talentcard.wechat.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/17 14:33
 * @Description: 微信前端Js服务用的sevice层接口
 */
public interface IJsSdkService {
    ResultVO getSignature(String openId);
}
