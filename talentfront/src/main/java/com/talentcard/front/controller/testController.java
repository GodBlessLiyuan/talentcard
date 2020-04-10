package com.talentcard.front.controller;


import com.talentcard.common.exception.WechatException;
import com.talentcard.front.utils.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @RequestMapping("test")
    public void test() {
    }
}
