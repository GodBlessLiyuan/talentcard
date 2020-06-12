package com.talentcard.wechat.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.config.WxConfig;
import com.talentcard.wechat.utils.AccessTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/10 09:28
 * @description accessToken
 */
@RequestMapping("accessToken")
@RestController
public class AccessTokenController {
    @Autowired
    private WxConfig wxConfig;
    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    @GetMapping("getToken")
    public ResultVO getAccessToken(@RequestHeader String token) {
        if(!StringUtils.equals(this.wxConfig.getCheckToken(), token)){
            return null;
        }
        String accessToken = AccessTokenUtil.getAccessToken();
        if (accessToken == null || accessToken.equals("")) {
            return new ResultVO(2200);
        }
        return new ResultVO(1000, accessToken);
    }
}
