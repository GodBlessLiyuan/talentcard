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
        Map<String,String> times=new HashMap<>(3);
        times.put("updateTime",updateTime);
        times.put("start",updateTime+" 00:00:00");
        times.put("end",updateTime+" 23:59:59");
        List<FarmhouseDailyPO> originFarmhouseDailyPOS = talentFarmhouseMapper.queryByUpdateTime(times);
        boolean originFlag=originFarmhouseDailyPOS==null||originFarmhouseDailyPOS.size()==0;
        //源数据为空
        if(originFlag){
            return;
        }
        //日活动当天的数据被覆盖
        List<FarmhouseDailyPO> toFarmhouseDailyPOS=farmhouseDailyMapper.queryByDailyTime(updateTime);
        boolean toFlag=toFarmhouseDailyPOS==null||toFarmhouseDailyPOS.size()==0;
        //目标数据为空
        if(toFlag){
            farmhouseDailyMapper.batchInsert(originFarmhouseDailyPOS);
            logger.info("批量写入了{}条记录",originFarmhouseDailyPOS.size());
            return;
        }
        //目标数据不为空，则覆盖目标数据；为了源和目标匹配，构造map结构数据
        Map<String,FarmhouseDailyPO> originMap=new HashMap<>(originFarmhouseDailyPOS.size());
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        String key="";
        for(FarmhouseDailyPO originPO:originFarmhouseDailyPOS){
            key=format.format(originPO.getDailyTime())+originPO.getFarmhouseId();
            originMap.put(key,originPO);
        }
        List<FarmhouseDailyPO> upFarmhouseDailyPOS=new ArrayList<>();
        for(FarmhouseDailyPO toPO:toFarmhouseDailyPOS){
            //目标数据的key
            key=format.format(toPO.getDailyTime())+toPO.getFarmhouseId();
            //目标与源数据的比较
            toPO.setNumber(originMap.get(key).getNumber());//人数
            toPO.setTimes(originMap.get(key).getTimes());//次数
            upFarmhouseDailyPOS.add(toPO);
            //如果目标的数据多，源数据少，也就是源有新增的农家乐数据，则找出新增的，执行新增操作
            originMap.remove(key);
        }
        if(originMap.size()>0){
            List<FarmhouseDailyPO> insertFarmhouseDailyPOS=new ArrayList<>(originMap.size());
            insertFarmhouseDailyPOS.addAll(originMap.values());
            int flag=farmhouseDailyMapper.batchInsert(insertFarmhouseDailyPOS);
            if(flag<1){
                logger.info("批量更新m_farmhouse_daily失败");
            }
        }
        if(upFarmhouseDailyPOS.size()>0){
            //批量更新
            for(FarmhouseDailyPO po:upFarmhouseDailyPOS){
                farmhouseDailyMapper.updateByPrimaryKey(po);
            }
            logger.info("批量更新了{}条记录",upFarmhouseDailyPOS.size());
        }

    }

}
