package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.FarmhouseDailyMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-16 09:03
 */
@Service
public class FarmhouseUseDailyServiceImpl implements IFarmhouseUseDailyService {
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    @Autowired
    private FarmhouseDailyMapper farmhouseDailyMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO init_daily() {
        //分组得到天数
        List<HashMap<String, String>> updateTimes=talentFarmhouseMapper.groupByUpdateTime();
        if(updateTimes==null||updateTimes.size()==0){
            return new ResultVO(1000,"t_talent_farmhouse表中没有数据");
        }
        List<FarmhouseDailyPO> farmhouseDailyPOS=new ArrayList<>();
        Map<String,String> times=new HashMap();
        for(HashMap<String,String> map:updateTimes){
            //当前天数
            times.put("updateTime",map.get("updateTime"));
            //构造时间
            times.put("start",map.get("updateTime")+" 00:00:00");
            times.put("end",map.get("updateTime")+" 23:59:59");
            //list.addAll(list)
            farmhouseDailyPOS.addAll(talentFarmhouseMapper.queryByUpdateTime(times));
        }
        if(farmhouseDailyPOS.size()==0){
            return new ResultVO(1000,"t_talent_farmhouse表中没有数据");
        }
        int flag=farmhouseDailyMapper.batchInsert(farmhouseDailyPOS);
        if(flag==0){
            return new ResultVO(2000,"批量插入失败");
        }
        return new ResultVO(1000,"批量写入了"+farmhouseDailyPOS.size()+"条数据");
    }
}
