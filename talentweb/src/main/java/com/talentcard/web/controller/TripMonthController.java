package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-22 10:46
 */
@RestController
@RequestMapping("trip_month")
public class TripMonthController {
    @Autowired
    private ITripMonthService tripMonthService;
    @PostMapping("init_month")
    public ResultVO init_month(){
        return tripMonthService.init_month();
    }

}
