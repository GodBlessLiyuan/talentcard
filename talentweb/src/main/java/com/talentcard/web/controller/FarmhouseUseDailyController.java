package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseDailyService;
import com.talentcard.web.utils.DateInitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    @PostMapping("query")
    public ResultVO query(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end
    ){
        Map<String,Object> map=new HashMap<>(3);
        map.put("name",name);
        map.put("start",start);
        map.put("end",end);
        return farmhouseUseDailyService.query(pageNum,pageSize,map);
    }
    //日统计和月统计：全部的
    @GetMapping("export")
    public ResultVO export(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end,
            HttpServletResponse response
    )
    {
        Map<String,Object> map=new HashMap<>(1);
        return farmhouseUseDailyService.export(map,response);
    }
}
