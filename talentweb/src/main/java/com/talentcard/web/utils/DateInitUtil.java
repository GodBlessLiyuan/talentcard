package com.talentcard.web.utils;

import com.talentcard.common.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description:获取之前时间的工具类
 * @author: liyuan
 * @data 2020-07-16 20:37
 */
public class DateInitUtil {
    private static SimpleDateFormat ZeroFormat=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static String initNMonthTime(int n){
        Calendar line=Calendar.getInstance();
        line.add(Calendar.MONTH,-n);
        return ZeroFormat.format(line.getTime());
    }
    /***
     * 参数格式类似：yyyy-MM
     * */
    public static String[] getMonthFristAndLastByCurrenDay(String updateTime) {
        String[] monthFristAndLastByCurrenDay=new String[2];
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(DateUtil.str2Date(updateTime,DateUtil.YHM));
        String start=updateTime+"-01 00:00:00";//当月的开始时间
        monthFristAndLastByCurrenDay[0]=start;
        int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String end=updateTime+"-"+dayMax+" 23:59:59";//当月的结束时间
        monthFristAndLastByCurrenDay[1]=end;
        return monthFristAndLastByCurrenDay;
    }

}
