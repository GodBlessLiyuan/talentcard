package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IEventFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 10:13
 * @description
 */
@RestController
@RequestMapping("eventField")
public class EventFieldController {
    @Autowired
    private IEventFieldService iEventFieldService;
    /**
     * 查询所有场地
     * @return
     */
    @RequestMapping("queryAll")
    public ResultVO queryAll() {
        return iEventFieldService.queryAll();
    }
}
