package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:31
 */
@RestController
@RequestMapping("userfeedback")
public class UserFeedbackController {

    @Autowired
    private IUserFeedbackService userFeedbackService;
    @PostMapping("query")
    public ResultVO query(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                          @RequestParam(value = "pageType",required = false)Byte pageType,
                          @RequestParam(value = "chooseItem",required = false)String chooseItem)
    {
        Map<String,Object> map=new HashMap<>();
        map.put("pageType",pageType);
        map.put("chooseItem",chooseItem);
        return userFeedbackService.query(pageNum,pageSize,map);
    }
}
