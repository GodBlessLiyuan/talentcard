package com.talentcard.web.controller;

import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripMonthService;
import com.talentcard.web.utils.DateInitUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
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
    @Value("${trip_month.count_date}")
    private String  month_time;
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
     * 思路：跟查询的一样，只是少了分页的条件,
     *   新增总人数的统计
     * */
    @PostMapping("total")
    public ResultVO total(
        @RequestParam(value = "name",required = false)String name,
        @RequestParam(value = "start",required = false)String start,
        @RequestParam(value = "end",required = false)String end
    ){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> numbersMap=new HashMap<>();
        if(!StringUtils.isEmpty(name)){
            map.put("name",name);
            numbersMap.put("name",name);
        }
        if(!StringUtils.isEmpty(start)){
            //start为01号，数据库的当月都是01号
            int len=start.length()- 2;
            map.put("start",start.substring(0,len)+"01");
            numbersMap.put("start",start+ " 00:00:00");
        }
        //end为28号，
        if(!StringUtils.isEmpty(end)){
            int len=end.length()- 2;
            map.put("end",end.substring(0,len)+"28");
            /**
             * 因为前端传的结束时间是1号或者28号，则后台做处理；
             * 如果年月小于当前，则end按照最后一天算，否则年月按照当前算
             */
            String currentYM=DateUtil.date2Str(new Date(),DateUtil.YHM);//当前年月
            Calendar calendar=Calendar.getInstance();
            if(end.substring(0,len).compareTo(currentYM)<0){
                calendar.setTime(DateUtil.str2Date(end,DateUtil.YMD));
                int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                end=end.substring(0,len)+day+" 23:59:59";//不是当前月的最后一天
            }else{
                calendar.add(Calendar.DAY_OF_MONTH,-1);//当前月的昨天
                end= DateUtil.date2Str(calendar.getTime(),DateUtil.YMD)+" 23:59:59";//定时任务计算的是23:59:59，保持和M_trip_month一致
            }
        }else{
            /**
             * 构造为当前的日，还要根据当前时间重构为定时的时分秒
             * */
            Calendar calendar=Calendar.getInstance();//给一个截止日期的默认值：昨天
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            end= DateUtil.date2Str(calendar.getTime(),DateUtil.YMD)+" 23:59:59";
        }
        numbersMap.put("end",end);
        return tripMonthService.total(map,numbersMap);
    }
}
