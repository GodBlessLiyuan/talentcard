package com.talentcard.front.controller;

import com.talentcard.common.pojo.User;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/24 14:47
 * @description 人才卡前端用户登录模块
 */

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping(value = "register")
    @ResponseBody
    public ResultVO<Object> register(@RequestBody User user) {
            iUserService.insert(user);
            return new ResultVO(1000, null);
    }
}
