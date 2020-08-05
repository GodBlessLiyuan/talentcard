package com.talentcard.web.controller;

import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseMonthService;
import com.talentcard.web.utils.DateInitUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
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
            int len=end.length()- 2;
            map.put("end",end.substring(0,len)+"28");
        }
        return farmhouseUseMonthService.query(pageNum,pageSize,map);
    }
    /**
     * 农家乐的人数和次数查询的是原表，
     * */
    @PostMapping("total")
    public ResultVO total(
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
            map.put("start",start.substring(0,len)+"01 00:00:00");
        }
        if(!StringUtils.isEmpty(end)){
            int len=end.length()- 2;
            //传入的年月的最后一天
            String currentYM=DateUtil.date2Str(new Date(),DateUtil.YHM);//当前年月
            Calendar calendar = Calendar.getInstance();//
            if(end.substring(0,len).compareTo(currentYM)<0){
                calendar.setTime(DateUtil.str2Date(end,DateUtil.YMD));//构造传入的年月日
                int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                end=end.substring(0,len)+day+" 23:59:59";//不是当前月，则为历史月的最后一天
            }else{
                calendar.add(Calendar.DAY_OF_MONTH,-1);//当前月的昨天
                end= DateUtil.date2Str(calendar.getTime(),DateUtil.YMD)+" 23:59:59";//定时任务计算的是23:59:59，保持和M_farmhouse_month一致
            }
        }else{
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,-1);//当前月的昨天
            end= DateUtil.date2Str(calendar.getTime(),DateUtil.YMD)+" 23:59:59";
        }
        map.put("end",end);
        return farmhouseUseMonthService.total(pageNum,pageSize,map);
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
