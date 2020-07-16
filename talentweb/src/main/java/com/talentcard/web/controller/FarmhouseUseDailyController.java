package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:农家乐数据的日统计
 * @author: liyuan
 * @data 2020-07-15 21:20
 * 初始化数据，定时任务叠加数据，查询数据，导出数据
 */
@RestController
@RequestMapping("farmhouse_use_daily")
public class FarmhouseUseDailyController {
    /***
     * 日统计，首先按照天分组之后，再次按照农家乐名字分组，然后再次统计
     * */
    @Autowired
    private IFarmhouseUseDailyService farmhouseUseDailyService;
    @PostMapping("init_daily")
    public ResultVO init_daily(){
        return farmhouseUseDailyService.init_daily();
    }
}
