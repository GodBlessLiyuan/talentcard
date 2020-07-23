package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TripDailyMapper;
import com.talentcard.common.pojo.TripDailyPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.TripDailyMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description: 旅游的日统计的定时任务
 * @author: liyuan
 * @data 2020-07-21 21:39
 */
@Service
public class TripDailyScheduleService {
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private TripDailyMapper tripDailyMapper;
    /**
     * 统计合适的当日的旅游数据，覆盖到响应的数据库
     * */
    @Scheduled(cron = "${trip_daily.count_time}")
    @Transactional(rollbackFor = Exception.class)
    public ResultVO daily_count(){
        String updateTime= DateUtil.date2Str(new Date(),DateUtil.YMD);
        return this.dailyShareCount(updateTime);
    }

    private ResultVO dailyShareCount(String updateTime) {
        Map<String,Object> times=new HashMap<>(5);
        times.put("updateTime", updateTime);
        //构造时间
        times.put("start", updateTime + " 00:00:00");
        times.put("end", updateTime + " 23:59:59");
        List<TripDailyPO> originTripDailyPOS = talentActivityHistoryMapper.getDailyPOS(times);//还缺免费次数、折扣次数和总次数
        if (originTripDailyPOS == null || originTripDailyPOS.size() == 0) {
            return new ResultVO(1000,"旅游日统计-定时任务-源数据没有数据");
        }
        for (TripDailyPO dailyPO:originTripDailyPOS) {
            times.put("sid",dailyPO.getSid());//景区ID
            times.put("status",2);//已经使用
            Long free=talentActivityHistoryMapper.getFreeOrDiscount(times);
            dailyPO.setFreeTimes(free);
            times.put("status",3);//折扣
            Long discount=talentActivityHistoryMapper.getFreeOrDiscount(times);
            dailyPO.setDiscountTimes(discount);
            Long total=free!=null?(discount!=null?free+discount:free):discount;
            dailyPO.setTotalTimes(total);
            //目标数据查询
            TripDailyPO toTripDailyPO =tripDailyMapper.getBySidDaily(dailyPO.getSidDaily());
            if(toTripDailyPO==null){
                dailyPO.setUpdateTime(new Date());
                tripDailyMapper.insert(dailyPO);
            }else{
                toTripDailyPO.setNumbers(dailyPO.getNumbers());
                toTripDailyPO.setFreeTimes(dailyPO.getFreeTimes());
                toTripDailyPO.setDiscountTimes(dailyPO.getDiscountTimes());
                toTripDailyPO.setTotalTimes(dailyPO.getTotalTimes());
                toTripDailyPO.setUpdateTime(new Date());
                tripDailyMapper.updateByPrimaryKey(toTripDailyPO);
            }

        }
        return new ResultVO(1000, "旅游的日统计新增和修改了" + originTripDailyPOS.size() + "条数据");
    }

    /**
     * 选择日的某一时间点，构造昨天的年月日之后，重复执行上面操作
     * */
    @Scheduled(cron = "${trip_daily.lastDay_count_time}")
    @Transactional(rollbackFor = Exception.class)
    public ResultVO lastDay_count(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String updateTime=DateUtil.date2Str(calendar.getTime(),DateUtil.YMD);
        return this.dailyShareCount(updateTime);
    }
}
