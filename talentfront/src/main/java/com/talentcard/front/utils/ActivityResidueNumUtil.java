package com.talentcard.front.utils;

import com.talentcard.common.mapper.ActivityResidueNumMapper;
import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TalentTripMapper;
import com.talentcard.common.pojo.ActivityResidueNumPO;
import com.talentcard.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 16:10
 * @description 福利活动剩余人数管理Util
 */
@Component
public class ActivityResidueNumUtil {
    private static ActivityResidueNumMapper activityResidueNumMapper;
    private static TalentActivityHistoryMapper talentActivityHistoryMapper;
    private static TalentTripMapper talentTripMapper;

    @Autowired
    public void setActivityResidueNumMapper(ActivityResidueNumMapper arMapper) {
        activityResidueNumMapper = arMapper;
    }

    @Autowired
    public void setTalentActivityHistoryMapper(TalentActivityHistoryMapper taMapper) {
        talentActivityHistoryMapper = taMapper;
    }

    @Autowired
    public void setTalentTripMapper(TalentTripMapper talentTripMapper) {
        talentTripMapper = talentTripMapper;
    }

    /**
     * 获得当前剩余人数
     *
     * @return
     */
    public static Long getResidueNum() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String time = year + "-" + month;
        ActivityResidueNumPO activityResidueNumPO = activityResidueNumMapper.findOne(time);
        Long num;
        //activityResidueNumPO，用总人数添加一条记录
        //说明表里没有这个月份的记录，第一个人，剩余人数 = 总人数
        if (activityResidueNumPO == null) {
            activityResidueNumPO = new ActivityResidueNumPO();
            activityResidueNumPO.setTime(time);
            //取得总人数
            num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        }
        num = activityResidueNumPO.getNum();
        return num;
    }

    /**
     * 剩余人数-1minusOneResidueNum
     */
    public static void minusOneResidueNum() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String time = year + "-" + month;
        ActivityResidueNumPO activityResidueNumPO = activityResidueNumMapper.findOne(time);
        Long num;
        //activityResidueNumPO，用总人数添加一条记录
        if (activityResidueNumPO == null) {
            activityResidueNumPO = new ActivityResidueNumPO();
            activityResidueNumPO.setTime(time);
            num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
            activityResidueNumPO.setNum(num - 1);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        } else {
            num = activityResidueNumPO.getNum() - 1;
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.updateByPrimaryKeySelective(activityResidueNumPO);
        }
    }

    /**
     * 剩余人数修正
     */
    public static void reviseNum() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int lastDay;
        if (month == 2) {
            lastDay = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        String time = year + "-" + month;
        //决定一个月的第一天和最后一天
        String startTime = year + "-" + month + "-01 00:00:00";
        String endTime = year + "-" + month + "-" + lastDay + " 23:59:59";

        ActivityResidueNumPO activityResidueNumPO = activityResidueNumMapper.findOne(time);
        //计算实际剩余次数
        Long num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
        Long vertifyTimes = talentTripMapper.getCostTimes(startTime, endTime);
        //有人使用过当前余额 = 总人数 - 已经使用的人数
        num = num - vertifyTimes;
        //剩余人数>总人数，剩余人数修正到总人数，为0，则强行一致
        if (num < 0) {
            num = (long) 0;
        }
        //没人使用过，则认为无记录，当前余额 = 总人数
        if (activityResidueNumPO == null) {
            activityResidueNumPO = new ActivityResidueNumPO();
            activityResidueNumPO.setTime(time);
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        } else {
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.updateByPrimaryKeySelective(activityResidueNumPO);
        }
    }
}
