package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TripDailyMapper;
import com.talentcard.common.pojo.TripDailyPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 19:20
 */
@Service
public class TripDailyServiceImpl implements ITripDailyService {

    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    private TripDailyMapper tripDailyMapper;

    /**
     * 按天分组，天之后分组景点id，将数据全部搬到对应表中
     */
    @Override
    public ResultVO init_daily() {
        List<HashMap<String, String>> dailys = talentActivityHistoryMapper.groupDailyByTime();
        if (dailys == null || dailys.size() == 0) {
            return new ResultVO(1000, "表t_talent_activity_history中无数据");
        }
        List<TripDailyPO> tripDailyPOS = new ArrayList<>();
        Map<String, Object> times = new HashMap(3);
        for (HashMap<String, String> daily : dailys) {
            //当前天数
            times.put("updateTime", daily.get("updateTime"));
            //构造时间
            times.put("start", daily.get("updateTime") + " 00:00:00");
            times.put("end", daily.get("updateTime") + " 23:59:59");
            List<TripDailyPO> dailyPOS = talentActivityHistoryMapper.getDailyPOS(times);//还缺免费次数、折扣次数和总次数
            if (dailyPOS != null || dailyPOS.size() > 0) {
                for (TripDailyPO dailyPO:dailyPOS) {
                    times.put("sid",dailyPO.getSid());//景区ID
                    times.put("status",2);//已经使用
                    Long free=talentActivityHistoryMapper.getFreeOrDiscount(times);
                    dailyPO.setFreeTimes(free);
                    times.put("status",3);//折扣
                    Long discount=talentActivityHistoryMapper.getFreeOrDiscount(times);
                    dailyPO.setDiscountTimes(discount);
                }
            }
            tripDailyPOS.addAll(dailyPOS);
        }
        if (tripDailyPOS == null || tripDailyPOS.size() == 0) {
            return new ResultVO(1000, "表t_talent_activity_history中无数据");
        }
        int flag = tripDailyMapper.batchInsert(tripDailyPOS);
        if (flag < 1) {
            return new ResultVO(2000, "批量更新失败");
        }
        return new ResultVO(1000, "批量新增了" + tripDailyPOS.size() + "条数据");
    }

    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<TripDailyPO> page= PageHelper.startPage(pageNum,pageSize);
        List<TripDailyPO> tripDailyPOS = tripDailyMapper.query(map);
    }
}
