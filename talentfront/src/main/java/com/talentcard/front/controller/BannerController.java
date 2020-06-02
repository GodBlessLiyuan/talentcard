package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-02 10:12
 * @description
 */
@RequestMapping("banner")
@RestController
public class BannerController {
    @Autowired
    IBannerService iBannerService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("query")
    public ResultVO query(){
        return iBannerService.query();
    }
}
