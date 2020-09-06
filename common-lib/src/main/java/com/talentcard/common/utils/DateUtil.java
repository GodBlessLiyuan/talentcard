package com.talentcard.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 18:54
 * @description: 时间处理工具
 * @version: 1.0
 */
public class DateUtil {
    public static final SimpleDateFormat YMD_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat YMD_000 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YHM = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat HM = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat YM = new SimpleDateFormat("yyyy.MM");
    public static final SimpleDateFormat YHM_NO = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat YMD_ = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * str 转 date
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }

        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * date 转 str
     *
     * @param date
     * @param sdf
     * @return
     */
    public static String date2Str(Date date, SimpleDateFormat sdf) {
        if (null == date) {
            return "";
        }

        return sdf.format(date);
    }
}
