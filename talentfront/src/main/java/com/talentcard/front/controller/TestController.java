package com.talentcard.front.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/24 10:50
 * @description 人才地图前端模块
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String testHello(){
        return  ("hello world");
    }
}
