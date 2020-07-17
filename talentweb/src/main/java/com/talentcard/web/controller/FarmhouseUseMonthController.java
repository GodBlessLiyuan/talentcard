package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseMonthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-17 09:26
 */
@RestController
@RequestMapping("farmhouse_month")
public class FarmhouseUseMonthController {
    @Autowired
    private IFarmhouseUseMonthService farmhouseUseMonthService;

    @PostMapping("initMonth")
    public ResultVO initMonth(){
        return farmhouseUseMonthService.initMonth();
    }
    @PostMapping("query")
    public ResultVO query(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end
    ){
        Map<String,Object> map=new HashMap<>();
        if(!StringUtils.isEmpty(name)){
            map.put("name",name.replace(" ",""));//替换前后和中间的空格
        }
        if(!StringUtils.isEmpty(start)){
            //start为01号，数据库的当月都是01号
            int len=start.length()- 2;
            map.put("start",start.substring(0,len)+"01");
        }
        //end为28号，
        if(!StringUtils.isEmpty(end)){
            int len=start.length()- 2;
            map.put("end",end.substring(0,len)+"28");
        }
        return farmhouseUseMonthService.query(pageNum,pageSize,map);
    }

    @GetMapping("export")
    public ResultVO export(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end,
            HttpServletResponse response
    ){
        Map<String,Object> map=new HashMap<>(1);
        return farmhouseUseMonthService.export(map,response);
    }
}
