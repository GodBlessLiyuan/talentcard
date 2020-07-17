package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.FarmhouseDailyMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    public void dailyCount(){
        //构造当天的时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String updateTime=simpleDateFormat.format(new Date());
        Map<String,String> times=new HashMap<>();
        times.put("updateTime",updateTime);
        times.put("start",updateTime+" 00:00:00");
        times.put("end",updateTime+" 23:59:59");
        List<FarmhouseDailyPO> originFarmhouseDailyPOS = talentFarmhouseMapper.queryByUpdateTime(times);
        //日活动当天的数据被覆盖
        List<FarmhouseDailyPO> toFarmhouseDailyPOS=farmhouseDailyMapper.queryByDailyTime(updateTime);
        boolean toFlag=toFarmhouseDailyPOS==null||toFarmhouseDailyPOS.size()==0;
        boolean originFlag=originFarmhouseDailyPOS==null||originFarmhouseDailyPOS.size()==0;
        //源数据不为空
        if(!originFlag){
            //目标数据为空
            if(toFlag){
                farmhouseDailyMapper.batchInsert(originFarmhouseDailyPOS);
                logger.info("批量写入了{}条记录",originFarmhouseDailyPOS.size());
            }
            //目标数据不为空，则覆盖目标数据
            else{
                //为了源和目标匹配，构造map结构数据
                Map<String,FarmhouseDailyPO> originMap=new HashMap<>();
                SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
                String key="";
                for(FarmhouseDailyPO originPO:originFarmhouseDailyPOS){
                    key=format.format(originPO.getDailyTime())+originPO.getFarmhouseId();
                    originMap.put(key,originPO);
                }
                List<FarmhouseDailyPO> upFarmhouseDailyPOS=new ArrayList<>();
                for(FarmhouseDailyPO toPO:toFarmhouseDailyPOS){
                    key=format.format(toPO.getDailyTime())+toPO.getFarmhouseId();
                    boolean upFlag1=originMap.get(key).getNumber()!=toPO.getNumber();
                    boolean upFlag2=originMap.get(key).getTimes()!=toPO.getTimes();
                    //只要有一个不相等则更新
                    if(upFlag1||upFlag2){
                        toPO.setNumber(originMap.get(key).getNumber());//人数
                        toPO.setTimes(originMap.get(key).getTimes());//次数
                        upFarmhouseDailyPOS.add(toPO);
                    }
                }
                if(upFarmhouseDailyPOS.size()>0){
                    //批量更新
                    int affect=farmhouseDailyMapper.batchUpdate(upFarmhouseDailyPOS);
                    if(affect>0){
                        logger.info("批量更新了{}条记录",upFarmhouseDailyPOS.size());
                    }else {
                        logger.info("批量更新失败：{}", JSONObject.toJSONString(upFarmhouseDailyPOS));
                    }

                }

            }
        }

    }

}
