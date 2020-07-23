package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.FarmhouseMonthMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseMonthPO;
import com.talentcard.common.utils.DateUtil;
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
    @Transactional(rollbackFor = Exception.class)
    public void MonthCount() {
        //判断当前的日，如果是1号，则构造上月的日期，
        Calendar calendar=Calendar.getInstance();
        if(calendar.get(Calendar.DAY_OF_MONTH)==1){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
        }
        //构造月的第一天和最后一天
        Map<String,String> times=new HashMap<>(4);
        String updateTime= DateUtil.date2Str(calendar.getInstance().getTime(),DateUtil.YHM);
        times.put("updateTime",updateTime+"-01");
        String[] monthFristAndLastByCurrenDay= DateInitUtil.getMonthFristAndLastByCurrenDay(updateTime);
        times.put("start",monthFristAndLastByCurrenDay[0]);
        times.put("end",monthFristAndLastByCurrenDay[1]);
        times.put("updateTimeSQL",updateTime);
        List<FarmhouseMonthPO> originFarmhouseMonthPOS = talentFarmhouseMapper.getMonthCountByUpdateTime(times);
        if(originFarmhouseMonthPOS==null||originFarmhouseMonthPOS.size()==0){
            return;
        }
        List<FarmhouseMonthPO> toFarmhouseMonthPOS=farmhouseMonthMapper.queryByMonth(updateTime+"-01");
        if(toFarmhouseMonthPOS==null||toFarmhouseMonthPOS.size()==0){
            farmhouseMonthMapper.batchInsert(originFarmhouseMonthPOS);
            logger.info("m_farmhouse_month批量写入了{}条记录",originFarmhouseMonthPOS.size());
            return;
        }
        toFarmhouseMonthPOS=null;
        //遍历源数据,对着目标，源数据被修改或者新增，
        for(FarmhouseMonthPO originPO:originFarmhouseMonthPOS){
            FarmhouseMonthPO toFarmhouseMonthPO=farmhouseMonthMapper.queryByDailyFarmHouseID(originPO.getMonthFarmhouseID());
            if(toFarmhouseMonthPO==null){
                originPO.setUpdateTime(new Date());
                farmhouseMonthMapper.insert(originPO);
            }else{
                toFarmhouseMonthPO.setNumber(originPO.getNumber());
                toFarmhouseMonthPO.setTimes(originPO.getTimes());
                toFarmhouseMonthPO.setUpdateTime(new Date());
                farmhouseMonthMapper.updateByPrimaryKeySelective(toFarmhouseMonthPO);
            }

        }
        logger.info("定时任务的农家乐月统计更新成功");
    }
}
