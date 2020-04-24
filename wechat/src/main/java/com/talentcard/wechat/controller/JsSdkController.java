package com.talentcard.wechat.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.IJsSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/10 09:28
 * @description jsSdk签名，根据openId，获取卡券相关的签名
 */
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
