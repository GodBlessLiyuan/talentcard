package com.talentcard.wechat.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.utils.AccessTokenUtil;
import org.springframework.web.bind.annotation.*;

@RequestMapping("accessToken")
@RestController
public class AccessTokenController {
    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    @GetMapping("getToken")
    public ResultVO getAccessToken() {
        String accessToken = AccessTokenUtil.getAccessToken();
        if (accessToken == null || accessToken == "") {
            return new ResultVO(2200);
        }
        return new ResultVO(1000, accessToken);
    }
}
