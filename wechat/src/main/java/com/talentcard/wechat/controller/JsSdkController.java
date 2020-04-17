package com.talentcard.wechat.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.IJsSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jsSdk")
@RestController
public class JsSdkController {
    @Autowired
    private IJsSdkService iJsSdkService;

    /**
     * 签名算法
     *
     * @return
     */
    @PostMapping("getSignature")
    public ResultVO getSignature(@RequestParam(value = "openId") String openId) {
        return iJsSdkService.getSignature(openId);
    }

}
