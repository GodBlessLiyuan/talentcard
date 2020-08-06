package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IHonourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-06 08:30
 * @description
 */
@RestController
@RequestMapping("honour")
public class HonourController {
    @Autowired
    private IHonourService iHonourService;
    /**
     * 查询
     * @return
     */
    @PostMapping("query")
    public ResultVO query() {
        return iHonourService.query();
    }
}
