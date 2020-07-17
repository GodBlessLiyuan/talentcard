package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.FarmhouseMonthMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseMonthPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:农家乐月统计的定时任务
 * @author: liyuan
 * @data 2020-07-17 11:26
 */
@Service
public class FarmHouseUserMonthScheduleService {
    /**
     * 统计出源数据，覆盖目标的当月数据，
     * 如果是每月的1号，则将数据覆盖到目标的上月数据
     * */
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    @Autowired
    private FarmhouseMonthMapper farmhouseMonthMapper;
    private static final Logger logger= LoggerFactory.getLogger(FarmHouseUserMonthScheduleService.class);
    @Scheduled(cron = "${farmhouse_use_month.count_time}")
    public void MonthCount() {
        //判断当前的日，如果是1号，则构造上月的日期，
        Calendar calendar=Calendar.getInstance();
        if(calendar.get(Calendar.DAY_OF_MONTH)==1){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
        }
        //构造月的第一天和最后一天
        Map<String,String> times=new HashMap<>(3);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        String updateTime=format.format(calendar.getTime());
        times.put("updateTime",updateTime+"-01");
        //构造当月的时间
        String start=updateTime+"-01 00:00:00";
        times.put("start",start);
        int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //当月的结束时间
        String end=updateTime+"-"+dayMax+" 23:59:59";
        times.put("end",end);
        List<FarmhouseMonthPO> originFarmhouseMonthPOS = talentFarmhouseMapper.getMonthCountByUpdateTime(times);
        if(originFarmhouseMonthPOS==null||originFarmhouseMonthPOS.size()==0){
            return;
        }
        List<FarmhouseMonthPO> toFarmhouseMonthPOS=farmhouseMonthMapper.queryByMonth(updateTime);
        if(toFarmhouseMonthPOS==null||toFarmhouseMonthPOS.size()==0){
            farmhouseMonthMapper.batchInsert(originFarmhouseMonthPOS);
            logger.info("m_farmhouse_month批量写入了{}条记录",originFarmhouseMonthPOS.size());
            return;
        }
        //构造map数据，执行更新操作，
        Map<String,FarmhouseMonthPO> originMap=new HashMap<>(originFarmhouseMonthPOS.size());
        String key="";
        format=new SimpleDateFormat("yyyy-MM-dd");
        for(FarmhouseMonthPO originPO:originFarmhouseMonthPOS){
            key=format.format(originPO.getMonth())+originPO.getName();//根据月时间和农家乐名构造key
            originMap.put(key,originPO);
        }
        List<FarmhouseMonthPO> updateFarmhouseMonthPOS=new ArrayList<>();
        //遍历目标数据
        for(FarmhouseMonthPO toPO:toFarmhouseMonthPOS){
            key=format.format(toPO.getMonth())+toPO.getName();
            boolean b1=toPO.getNumber()!=originMap.get(key).getNumber();
            boolean b2=toPO.getTimes()!=originMap.get(key).getTimes();
            if(b1||b2){
                toPO.setNumber(originMap.get(key).getNumber());
                toPO.setTimes(originMap.get(key).getTimes());
                updateFarmhouseMonthPOS.add(toPO);
            }
            //移除目标的对象
            originMap.remove(key);
        }
        if(updateFarmhouseMonthPOS.size()>0){
            for(FarmhouseMonthPO upPO:updateFarmhouseMonthPOS){
                farmhouseMonthMapper.updateByPrimaryKey(upPO);
            }
            logger.info("批量更新了{}条农家乐月统计数据",updateFarmhouseMonthPOS.size());
        }
        if(originMap.size()>0){
            List<FarmhouseMonthPO> insertFarmhouseMonthPOS=new ArrayList<>(originMap.size());
            insertFarmhouseMonthPOS.addAll(originMap.values());
            farmhouseMonthMapper.batchInsert(insertFarmhouseMonthPOS);
            logger.info("批量新增了{}条农家乐月统计数据",insertFarmhouseMonthPOS.size());
        }
    }
}
