package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TripMonthMapper;
import com.talentcard.common.pojo.TripMonthPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.utils.DateInitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-22 11:34
 */
@Service
public class TripMonthScheduleService {
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private TripMonthMapper tripMonthMapper;

    /**
     * 首先判断是第一天则将月份改为上一个月，之后统计相应月份的数据，搬移到响应数据库（一条条的插入或者更新）
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "${trip_month.count_time}")
    public ResultVO monthCount() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        //构造月的第一天和最后一天
        Map<String, Object> times = new HashMap<>();
        String updateTime = DateUtil.date2Str(calendar.getInstance().getTime(), DateUtil.YHM);
        times.put("updateTime", updateTime + "-01");
        String[] monthFristAndLastByCurrenDay = DateInitUtil.getMonthFristAndLastByCurrenDay(updateTime);
        times.put("start", monthFristAndLastByCurrenDay[0]);
        times.put("end", monthFristAndLastByCurrenDay[1]);
        times.put("updateTimeSQL", updateTime);
        List<TripMonthPO> originTripMonthPOS = talentActivityHistoryMapper.getMonthPOS(times);
        if (originTripMonthPOS == null && originTripMonthPOS.size() == 0) {
            return new ResultVO(1000, "表t_talent_activity_history中没有数据");
        }
        for (TripMonthPO originPO : originTripMonthPOS) {
            times.put("sid", originPO.getSid());//景区ID
            times.put("status", 2);//已经使用
            Long free = talentActivityHistoryMapper.getFreeOrDiscount(times);
            originPO.setFreeTimes(free);
            times.put("status", 3);//折扣
            Long discount = talentActivityHistoryMapper.getFreeOrDiscount(times);
            originPO.setDiscountTimes(discount);
            Long total = free != null ? (discount != null ? free + discount : free) : discount;
            originPO.setTotalTimes(total);
            TripMonthPO toTripMonthPO=tripMonthMapper.getBySidMonth(originPO.getSidMonth());
            if(toTripMonthPO==null){
                originPO.setUpdateTime(new Date());
                tripMonthMapper.insert(originPO);
            }else{
                toTripMonthPO.setNumbers(originPO.getNumbers());
                toTripMonthPO.setFreeTimes(originPO.getFreeTimes());
                toTripMonthPO.setDiscountTimes(originPO.getDiscountTimes());
                toTripMonthPO.setTotalTimes(originPO.getTotalTimes());
                toTripMonthPO.setUpdateTime(new Date());
                tripMonthMapper.updateByPrimaryKey(toTripMonthPO);
            }
        }
        return new ResultVO(1000,"旅游月统计的表更新和新增了"+originTripMonthPOS.size()+"条数据");
    }

}
