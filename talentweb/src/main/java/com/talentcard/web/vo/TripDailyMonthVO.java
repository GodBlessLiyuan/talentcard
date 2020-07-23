package com.talentcard.web.vo;

import com.talentcard.common.pojo.TripDailyPO;
import com.talentcard.common.pojo.TripMonthPO;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 21:15
 */
@Data
public class TripDailyMonthVO {
    private Long ID;
    private String time;
    private String name;
    private Long number;
    private Long freeTimes;
    private Long discountTimes;
    private Long totalTimes;

    public static List<TripDailyMonthVO> convert(List<TripDailyPO> tripDailyPOS) {
        if(tripDailyPOS==null||tripDailyPOS.size()==0){
            return null;
        }
        List<TripDailyMonthVO> vos=new ArrayList<>(tripDailyPOS.size());
        for(TripDailyPO po:tripDailyPOS){
            TripDailyMonthVO vo=new TripDailyMonthVO();
            vo.setID(po.getTdId());
            vo.setTime(DateUtil.date2Str(po.getDaily(),DateUtil.YMD));
            vo.setName(po.getScenicName());
            vo.setNumber(po.getNumbers());
            vo.setFreeTimes(po.getFreeTimes());
            vo.setDiscountTimes(po.getDiscountTimes());
            vo.setTotalTimes(po.getTotalTimes());
            vos.add(vo);
        }
        return vos;
    }

    public static List<TripDailyMonthVO> convertMonth(List<TripMonthPO> tripMonthPOS) {
        if(tripMonthPOS==null||tripMonthPOS.size()==0){
            return null;
        }
        List<TripDailyMonthVO> vos=new ArrayList<>(tripMonthPOS.size());
        for(TripMonthPO po:tripMonthPOS){
            TripDailyMonthVO vo=new TripDailyMonthVO();
            vo.setID(po.getTmId());
            vo.setTime(DateUtil.date2Str(po.getMonth(),DateUtil.YHM));
            vo.setName(po.getName());//景区名
            vo.setNumber(po.getNumbers());
            vo.setFreeTimes(po.getFreeTimes());
            vo.setDiscountTimes(po.getDiscountTimes());
            vo.setTotalTimes(po.getTotalTimes());
            vos.add(vo);
        }
        return vos;
    }
}
