package com.talentcard.miniprogram.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.UserFeedBackDTO;
import com.talentcard.miniprogram.service.IUserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:07
 */
@RestController
@RequestMapping("userfeedback")
public class UserFeedbackController {
    @Autowired
    private IUserFeedbackService userFeedbackService;
    @PostMapping("insert")
    public ResultVO insert(@RequestBody UserFeedBackDTO userFeedBackDTO){
        return userFeedbackService.insert(userFeedBackDTO);
    }
}
