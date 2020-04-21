package com.talentcard.wechat.test;

import com.talentcard.wechat.service.MessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 10:38
 * @description: TODO
 * @version: 1.0
 */
@SpringBootTest
public class TestWx {

    @Autowired
    private MessageService messageService;

    @Test
    public void sendTemplateMessage(){
        String openid = "oQetQ1SPRw04AxmI9OR55jOaNKQ8";
        System.out.println(messageService.sendTemplateMessage(openid));
    }



}

