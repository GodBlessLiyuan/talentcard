package com.talentcard.web;

import com.talentcard.common.utils.rabbit.RabbitUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/10/13 18:57
 * @description: 测试
 * @version: 1.0
 */
@RestController
public class TestController {
    @RequestMapping("send")
    public void sendMsg() {
        RabbitUtil.sendTrackMsg((byte) 1, (byte) 1, "xxx");
    }
}
