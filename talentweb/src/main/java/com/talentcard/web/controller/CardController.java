package com.talentcard.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("card")
@RestController
public class CardController {
    @Autowired
    private ICardService iCardService;

    @PostMapping("add")
    public ResultVO add(@RequestBody JSONObject jsonObject) {
        return iCardService.add(jsonObject);
    }
}
