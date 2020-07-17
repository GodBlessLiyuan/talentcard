package com.talentcard.web.utils;

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
}
