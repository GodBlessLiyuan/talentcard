package com.talentcard.miniprogram.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.utils.AccessTokenUtil;
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
    /**
     * 获取accessToken
     *
     * @return accessToken
     */
    @GetMapping("getToken")
    public ResultVO getAccessToken() {
        String accessToken = AccessTokenUtil.getAccessToken();
        if (accessToken == null || accessToken.equals("")) {
            return new ResultVO(2200);
        }
        return new ResultVO(1000, accessToken);
    }
}
