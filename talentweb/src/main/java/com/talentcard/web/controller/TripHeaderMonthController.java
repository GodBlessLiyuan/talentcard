package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripHeaderMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 15:07
 */
@RestController
@RequestMapping("trip_header_month")
public class TripHeaderMonthController {
    @Autowired
    private ITripHeaderMonthService tripHeaderMonthService;
    @PostMapping("count")
    public ResultVO count(){
        return tripHeaderMonthService.count();
    }
}
