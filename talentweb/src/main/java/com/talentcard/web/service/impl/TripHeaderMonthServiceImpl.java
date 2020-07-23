package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.TalentTripMapper;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.RedisUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripHeaderMonthService;
import com.talentcard.web.utils.DateInitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 15:08
 */
@Service
public class TripHeaderMonthServiceImpl implements ITripHeaderMonthService {
    /**
     * 已核销：已使用，t_talent_trip表的status==2
     * 已领取未使用：t_talent_trip表的status==1
     * */
    @Autowired
    private TalentTripMapper talentTripMapper;
    @Override
    public ResultVO count() {
        Long totalNumbers = Long.valueOf(RedisUtil.getConfigValue("SCENIC_NUM"));//本月总旅游数
        String[] monthFristAndLastByCurrenDay = DateInitUtil.getMonthFristAndLastByCurrenDay(DateUtil.date2Str(new Date(), DateUtil.YHM));
        Map<String,Object> map=new HashMap<>(3);
        map.put("start",monthFristAndLastByCurrenDay[0]);
        map.put("end",monthFristAndLastByCurrenDay[1]);
        map.put("status",1);//未使用
        Map<String,Object> result=new HashMap<>(4);
        Long usingNumbers=talentTripMapper.countUsedOrUsing(map);
        result.put("totalNumbers",totalNumbers);
        result.put("usingNumbers",usingNumbers);
        map.put("status",2);//已经使用
        Long usedNumbers=talentTripMapper.countUsedOrUsing(map);
        result.put("usedNumbers",usedNumbers);
        if(totalNumbers==null) totalNumbers=0L;
        long unreceived=totalNumbers-usingNumbers-usedNumbers;
        result.put("unreceived",unreceived);
        return new ResultVO(1000,result);
    }


}
