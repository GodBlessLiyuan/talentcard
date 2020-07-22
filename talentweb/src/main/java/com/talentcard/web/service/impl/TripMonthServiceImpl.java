package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TripMonthMapper;
import com.talentcard.common.pojo.TripMonthPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripDailyService;
import com.talentcard.web.service.ITripMonthService;
import com.talentcard.web.utils.DateInitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-22 10:54
 */
@Service
public class TripMonthServiceImpl implements ITripMonthService {

    /**
     * 按月分组，将每个月的数据计算好，搬移到响应的数据表
     * */
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private TripMonthMapper tripMonthMapper;

    @Override
    public ResultVO init_month() {
        List<HashMap<String,String>> months=talentActivityHistoryMapper.groupMonthByTime();
        if(months==null||months.size()==0){
            return new ResultVO(1000,"表t_talent_activity_history中没有数据");
        }
        Map<String,Object> times=new HashMap<>();
        List<TripMonthPO> allTripMonthPOS=new ArrayList<>();
        for(HashMap<String,String> month:months){
            String updateTime=month.get("updateTime");
            //因为sql是date类型，insert出错，所以加上一个虚的字符串：-01
            times.put("updateTime",updateTime+"-01");
            //构造当月的时间
            String[] monthFristAndLastByCurrenDay= DateInitUtil.getMonthFristAndLastByCurrenDay(updateTime);
            times.put("start",monthFristAndLastByCurrenDay[0]);
            times.put("end",monthFristAndLastByCurrenDay[1]);
            times.put("updateTimeSQL",updateTime);//为了构造：主键年-月形式
            List<TripMonthPO> originTripMonthPOS=talentActivityHistoryMapper.getMonthPOS(times);
            if(originTripMonthPOS!=null&&originTripMonthPOS.size()>0){
                for(TripMonthPO originPO:originTripMonthPOS){
                    times.put("sid",originPO.getSid());//景区ID
                    times.put("status",2);//已经使用
                    Long free=talentActivityHistoryMapper.getFreeOrDiscount(times);
                    originPO.setFreeTimes(free);
                    times.put("status",3);//折扣
                    Long discount=talentActivityHistoryMapper.getFreeOrDiscount(times);
                    originPO.setDiscountTimes(discount);
                    Long total=free!=null?(discount!=null?free+discount:free):discount;
                    originPO.setTotalTimes(total);
                }
            }
            allTripMonthPOS.addAll(originTripMonthPOS);
        }
        //实际上不可能为null或者0
        if(allTripMonthPOS.size()==0){
            return new ResultVO(1000,"表t_talent_activity_history中没有数据");
        }
        int flag=tripMonthMapper.batchInsert(allTripMonthPOS);
        if(flag<1){
            return new ResultVO(2000,"旅游批量新增失败了");
        }
        return new ResultVO(1000,"旅游批量新增了"+allTripMonthPOS.size()+"条数据");
    }
}
