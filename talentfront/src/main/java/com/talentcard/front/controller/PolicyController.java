package com.talentcard.front.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 15:22
 * @description: 政策权益
 * @version: 1.0
 */
@RequestMapping("policy")
@RestController
public class PolicyController {
    @Autowired
    private IPolicyService service;

    @RequestMapping("applies")
    public ResultVO applies(@RequestParam(name = "tid", defaultValue = "0") Long tid) {
        return service.applies(tid);
    }
}
