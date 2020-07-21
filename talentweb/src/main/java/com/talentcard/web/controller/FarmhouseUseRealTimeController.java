package com.talentcard.web.controller;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseRealTimeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-15 15:55
 */
@RestController
@RequestMapping("farmhouse_use_realtime")
public class FarmhouseUseRealTimeController {
    @Autowired
    private IFarmhouseUseRealTimeService farmhouseUseRealTimeService;

    @PostMapping("query")
    public ResultVO realtime_query(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "cardID",required = false)Integer cardID,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end
            )
    {
        Map<String,Object> map=new HashMap<>();
        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }
        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        map.put("name",name);
        map.put("cardID",cardID);
        map.put("start",start);
        map.put("end",end);
        return farmhouseUseRealTimeService.query(pageNum,pageSize,map);
    }
    /**
     * 跟搜索条件没有关系，固定为：
     * 实时数据：最近3个月的；
     * 日统计和月统计：全部的
     * */
    @RequestMapping("export")
    public ResultVO export(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "cardID",required = false)Integer cardID,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end,
            HttpServletResponse response
      ){
        Map<String,Object> map=new HashMap<>(1);
        String line=init3MonthTime();
        map.put("start",line);
        return farmhouseUseRealTimeService.export(map,response);
    }

    private String init3MonthTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar line=Calendar.getInstance();
        line.add(Calendar.MONTH,-3);
        return format.format(line.getTime());
    }
}
