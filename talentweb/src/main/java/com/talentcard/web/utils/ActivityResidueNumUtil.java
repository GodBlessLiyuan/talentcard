package com.talentcard.web.utils;

import com.talentcard.common.mapper.ActivityResidueNumMapper;
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

    @Autowired
    public void setActivityResidueNumMapper(ActivityResidueNumMapper arMapper) {
        activityResidueNumMapper = arMapper;
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
        if (activityResidueNumPO == null) {
            activityResidueNumPO.setTime(time);
            num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        }
        num = activityResidueNumPO.getNum();
        return num;
    }

    /**
     * 剩余人数-1
     */
    public static Integer minusOneResidueNum() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String time = year + "-" + month;
        ActivityResidueNumPO activityResidueNumPO = activityResidueNumMapper.findOne(time);
        Long num;
        //activityResidueNumPO，用总人数添加一条记录
        if (activityResidueNumPO == null) {
            activityResidueNumPO.setTime(time);
            num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
            activityResidueNumPO.setNum(num - 1);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        } else {
            //人数少于0不能再减了！！！这里核销需要失败
            num = activityResidueNumPO.getNum() - 1;
            if (num < 0) {
                return 100;
            }
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.updateByPrimaryKeySelective(activityResidueNumPO);
        }
        return 0;
    }

    /**
     * 剩余人数修正
     */
    public static void reviseNum(Long totalNum) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String time = year + "-" + month;
        ActivityResidueNumPO activityResidueNumPO = activityResidueNumMapper.findOne(time);
        Long num;
        //activityResidueNumPO，用总人数添加一条记录
        if (activityResidueNumPO == null) {
            activityResidueNumPO.setTime(time);
            num = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));
            activityResidueNumPO.setNum(num);
            activityResidueNumMapper.insertSelective(activityResidueNumPO);
        } else {
            //剩余人数>总人数，剩余人数修正到总人数
            if (activityResidueNumPO.getNum() > totalNum) {
                activityResidueNumPO.setNum(totalNum);
                activityResidueNumMapper.updateByPrimaryKeySelective(activityResidueNumPO);
            }
        }
    }
}
