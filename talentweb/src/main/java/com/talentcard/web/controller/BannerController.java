package com.talentcard.web.controller;

import com.talentcard.web.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:55
 * @description: banner配置
 * @version: 1.0
 */
@RequestMapping("banner")
@RestController
public class BannerController {
    @Autowired
    private IBannerService bannerService;
}
