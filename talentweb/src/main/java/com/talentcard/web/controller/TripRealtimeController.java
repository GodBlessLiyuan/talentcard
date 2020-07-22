package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripRealtimeService;
import com.talentcard.web.utils.DateInitUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 16:35
 */
@RequestMapping("trip_realtime")
@RestController
public class TripRealtimeController {
    @Autowired
    private ITripRealtimeService tripRealtimeService;
    @PostMapping("query")
    public ResultVO query(
       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
       @RequestParam(value = "scenicName",required = false)String scenicName,
       @RequestParam(value = "welfareType",required = false)Integer welfareType,
       @RequestParam(value = "cardID",required = false)Integer cardID,
       @RequestParam(value = "start",required = false)String start,
       @RequestParam(value = "end",required = false)String end
    ){
        if (!StringUtils.isEmpty(start)) {
            start = start + " 00:00:00";
        }
        if (!StringUtils.isEmpty(end)) {
            end = end + " 23:59:59";
        }
        Map<String,Object> map=new HashMap<>(4);
        map.put("scenicName",scenicName);
        map.put("welfareType",welfareType);//要问一下，数据库开始是2,3
        map.put("cardID",cardID);
        map.put("start",start);
        map.put("end",end);
        return tripRealtimeService.query(pageNum,pageSize,map);
    }
    @GetMapping("export")
    public ResultVO export(
            @RequestParam(value = "scenicName",required = false)String scenicName,
            @RequestParam(value = "welfareType",required = false)Integer welfareType,
            @RequestParam(value = "cardID",required = false)Integer cardID,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end,
            HttpServletResponse response
    ){
        Map<String,Object> map=new HashMap<>(1);
        String startTime = DateInitUtil.initNMonthTime(3);//最近3个月
        map.put("start",startTime);
        return tripRealtimeService.export(map,response);
    }
}
