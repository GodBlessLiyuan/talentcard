package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.FarmhouseDailyMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.utils.DateInitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-16 16:24
 */
@Service
public class FarmHouseUserScheduleService {

    private Logger logger= LoggerFactory.getLogger(FarmHouseUserScheduleService.class);
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    @Autowired
    private FarmhouseDailyMapper farmhouseDailyMapper;
    /**
     * 测试了目标数据为null；有目标数据但是number/times为null或者0；目标数据和源数据一致的三种情况；
     * 均无误
     * */
    @Scheduled(cron = "${farmhouse_use_daily.count_time}")
    @Transactional(rollbackFor = Exception.class)
    public void dailyCount(){
        //构造当天的时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String updateTime=simpleDateFormat.format(new Date());
        Map<String,String> times=new HashMap<>(3);
        times.put("updateTime",updateTime);
        times.put("start",updateTime+" 00:00:00");
        times.put("end",updateTime+" 23:59:59");
        List<FarmhouseDailyPO> originFarmhouseDailyPOS = talentFarmhouseMapper.queryByUpdateTime(times);
        //源数据为空
        if(originFarmhouseDailyPOS==null||originFarmhouseDailyPOS.size()==0){
            return;
        }
        //日活动当天的数据被覆盖
        List<FarmhouseDailyPO> toFarmhouseDailyPOS=farmhouseDailyMapper.queryByDailyTime(updateTime);
        //目标数据为空
        if(toFarmhouseDailyPOS==null||toFarmhouseDailyPOS.size()==0){
            farmhouseDailyMapper.batchInsert(originFarmhouseDailyPOS);
            logger.info("批量写入了{}条记录",originFarmhouseDailyPOS.size());
            return;
        }
        //遍历源数据，目标无则新增数据，目标有则更新数据
        for(FarmhouseDailyPO originPO:originFarmhouseDailyPOS){
            FarmhouseDailyPO toFarmhouseDailyPO=farmhouseDailyMapper.queryByDailyFarmHouseID(originPO.getDailyFarmHouseID());
            if(toFarmhouseDailyPO==null){
                originPO.setUpdateTime(new Date());
                farmhouseDailyMapper.insert(originPO);
            }else{
                toFarmhouseDailyPO.setNumber(originPO.getNumber());
                toFarmhouseDailyPO.setTimes(originPO.getTimes());
                farmhouseDailyMapper.updateByPrimaryKeySelective(toFarmhouseDailyPO);
            }
        }
        logger.info("定时任务的农家乐日统计更新成功");
    }
    /**
     * 今天统计昨天的农家乐日统计
     * */
    @Scheduled(cron = "${farmhouse_use_daily.lastDay_count_time}")
    @Transactional(rollbackFor = Exception.class)
    public void lastDay_count_time(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String updateTime=DateUtil.date2Str(calendar.getTime(),DateUtil.YMD);
        Map<String,String> times=new HashMap<>(3);
        times.put("updateTime",updateTime);
        times.put("start",updateTime+" 00:00:00");
        times.put("end",updateTime+" 23:59:59");
        List<FarmhouseDailyPO> originFarmhouseDailyPOS = talentFarmhouseMapper.queryByUpdateTime(times);
        //源数据为空
        if(originFarmhouseDailyPOS==null||originFarmhouseDailyPOS.size()==0){
            return;
        }
        //日活动当天的数据被覆盖
        List<FarmhouseDailyPO> toFarmhouseDailyPOS=farmhouseDailyMapper.queryByDailyTime(updateTime);
        //目标数据为空
        if(toFarmhouseDailyPOS==null||toFarmhouseDailyPOS.size()==0){
            farmhouseDailyMapper.batchInsert(originFarmhouseDailyPOS);
            logger.info("批量写入了{}条记录",originFarmhouseDailyPOS.size());
            return;
        }
        //遍历源数据，目标无则新增数据，目标有则更新数据
        for(FarmhouseDailyPO originPO:originFarmhouseDailyPOS){
            FarmhouseDailyPO toFarmhouseDailyPO=farmhouseDailyMapper.queryByDailyFarmHouseID(originPO.getDailyFarmHouseID());
            if(toFarmhouseDailyPO==null){
                originPO.setUpdateTime(new Date());
                farmhouseDailyMapper.insert(originPO);
            }else{
                toFarmhouseDailyPO.setNumber(originPO.getNumber());
                toFarmhouseDailyPO.setTimes(originPO.getTimes());
                farmhouseDailyMapper.updateByPrimaryKeySelective(toFarmhouseDailyPO);
            }
        }
        logger.info("定时任务之昨天的农家乐日统计更新成功");
    }
}
