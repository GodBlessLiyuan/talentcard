package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripMonthService;
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
    @PostMapping("query")
    public ResultVO query(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end
    ){
        Map<String,Object> map=new HashMap<>(3);
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
            int len=end.length()- 2;
            map.put("end",end.substring(0,len)+"28");
        }
        return tripMonthService.query(pageNum,pageSize,map);
    }
    /**
     * 全部数据
     * */
    @GetMapping("export")
    public ResultVO export(
            @RequestParam(value = "name",required = false)String name,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required = false)String end,
            HttpServletResponse response
    ){
        Map<String,Object> map=new HashMap<>(1);
        return tripMonthService.export(map,response);
    }

    /**
     * 旅游的月统计：总计的显示的是当前筛选条件下的所有的汇总
     * 思路：跟查询的一样，只是少了分页的条件
     * */
    @PostMapping("total")
    public ResultVO total(
        @RequestParam(value = "name",required = false)String name,
        @RequestParam(value = "start",required = false)String start,
        @RequestParam(value = "end",required = false)String end
    ){
        Map<String,Object> map=new HashMap<>();
        if(!StringUtils.isEmpty(name)){
            map.put("name",name);
        }
        if(!StringUtils.isEmpty(start)){
            //start为01号，数据库的当月都是01号
            int len=start.length()- 2;
            map.put("start",start.substring(0,len)+"01");
        }
        //end为28号，
        if(!StringUtils.isEmpty(end)){
            int len=end.length()- 2;
            map.put("end",end.substring(0,len)+"28");
        }
        return tripMonthService.total(map);
    }
}
