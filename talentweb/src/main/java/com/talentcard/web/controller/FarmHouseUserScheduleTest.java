package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.impl.FarmHouseUserScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-16 18:50
 */
@RestController
@RequestMapping("farmHouseUserScheduleTest")
public class FarmHouseUserScheduleTest {
    @Autowired
    private FarmHouseUserScheduleService farmHouseUserScheduleService;
    @PostMapping("testDaily")
    public ResultVO testDaily(){
        farmHouseUserScheduleService.dailyCount();
        return new ResultVO(1000);
    }
}
