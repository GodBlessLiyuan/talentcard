package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 19:19
 */
@RestController
@RequestMapping("trip_daily")
public class TripDailyController {
    @Autowired
    private ITripDailyService tripDailyService;
    @PostMapping("init_daily")
    public ResultVO init_daily(){
        return tripDailyService.init_daily();
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
        if(name!=null){
            name=name.replace(" ","");
        }
        map.put("name",name);
        map.put("start",start);
        map.put("end",end);
        return tripDailyService.query(pageNum,pageSize,map);
    }
    @GetMapping("export")
    public ResultVO export(
        @RequestParam(value = "name",required = false)String name,
        @RequestParam(value = "start",required = false)String start,
        @RequestParam(value = "end",required = false)String end,
        HttpServletResponse response
    ){
        Map<String,Object> map=new HashMap<>(1);
        return tripDailyService.export(map,response);
    }
}
