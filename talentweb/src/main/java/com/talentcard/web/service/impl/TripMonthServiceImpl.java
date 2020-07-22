package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.mapper.TripMonthMapper;
import com.talentcard.common.pojo.TripMonthPO;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripDailyService;
import com.talentcard.web.service.ITripMonthService;
import com.talentcard.web.utils.DateInitUtil;
import com.talentcard.web.vo.TripDailyMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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
    private static final String[] EXPORT_TITLES={"时间","景区名称","使用人数","免费次数","折扣次数","总使用次数"};
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

    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<TripMonthPO> page= PageHelper.startPage(pageNum,pageSize);
        List<TripMonthPO> tripMonthPOS=tripMonthMapper.query(map);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), TripDailyMonthVO.convertMonth(tripMonthPOS)));
    }

    @Override
    public ResultVO export(Map<String, Object> map, HttpServletResponse response) {
        List<TripMonthPO> tripMonthPOS=tripMonthMapper.query(map);
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(TripDailyMonthVO.convertMonth(tripMonthPOS)), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<TripDailyMonthVO> vos) {
        if(vos==null||vos.size()==0){
            return null;
        }
        String[][] neiRongs=new String[vos.size()][EXPORT_TITLES.length];
        int num=0;
        for(TripDailyMonthVO vo:vos){
            neiRongs[num][0]=vo.getTime();
            neiRongs[num][1]=vo.getName();
            neiRongs[num][2]=vo.getNumber()+"";
            neiRongs[num][3]=vo.getFreeTimes()+"";
            neiRongs[num][4]=vo.getDiscountTimes()+"";
            neiRongs[num][5]=vo.getTotalTimes()+"";
            num++;
        }
        return neiRongs;
    }
}
