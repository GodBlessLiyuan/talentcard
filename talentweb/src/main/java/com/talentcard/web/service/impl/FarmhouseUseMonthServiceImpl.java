package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.FarmhouseMonthMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseMonthPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseMonthService;
import com.talentcard.web.utils.DateInitUtil;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.FarmhouseUseMonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-17 09:27
 */
@Service
public class FarmhouseUseMonthServiceImpl implements IFarmhouseUseMonthService {

    @Autowired
    private FarmhouseMonthMapper farmhouseMonthMapper;
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    private static final String[] EXPORT_TITLES={"时间","农家乐名称","使用人数","使用次数"};
    //先按照月分组，之后统计人数和次数
    @Override
    public ResultVO initMonth() {
        //先按月分组
        List<HashMap<String,String>> monthMaps=talentFarmhouseMapper.groupByMonthUseUpdateTime();
        if(monthMaps==null||monthMaps.size()==0){
            return new ResultVO(1000,"t_talent_farmhouse表中没有数据");
        }
        Map<String,String> times=new HashMap<>(4);
        List<FarmhouseMonthPO> farmhouseMonthPOS=new ArrayList<>();
        for(HashMap<String,String> monthMap:monthMaps){
            String updateTime=monthMap.get("updateTime");
            //因为sql是date类型，insert出错，所以加上一个虚的字符串：-01
            times.put("updateTime",updateTime+"-01");
            //构造当月的时间
            String[] monthFristAndLastByCurrenDay= DateInitUtil.getMonthFristAndLastByCurrenDay(updateTime);
            times.put("start",monthFristAndLastByCurrenDay[0]);
            times.put("end",monthFristAndLastByCurrenDay[1]);
            times.put("updateTimeSQL",updateTime);
            farmhouseMonthPOS.addAll(talentFarmhouseMapper.getMonthCountByUpdateTime(times));
        }
        if(farmhouseMonthPOS.size()>0){
            int affect=farmhouseMonthMapper.batchInsert(farmhouseMonthPOS);
            if(affect==0){
                return new ResultVO(2000,"批量写入表m_farmhouse_month失败");
            }
        }
        return new ResultVO(1000,"批量写入了"+farmhouseMonthPOS.size()+"条数据");
    }



    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<FarmhouseMonthPO> page = PageHelper.startPage(pageNum, pageSize);
        List<FarmhouseMonthPO> farmhouseMonthPOS=farmhouseMonthMapper.query(map);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), FarmhouseUseMonthVO.convert(farmhouseMonthPOS)));
    }

    @Override
    public ResultVO total(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        HashMap<String,Object> total=talentFarmhouseMapper.queryTotalByUpdateTime(map);
        return new ResultVO(1000,total);
    }


    @Override
    public ResultVO export(Map<String, Object> map, HttpServletResponse response) {
        List<FarmhouseMonthPO> farmhouseDailyPOS = farmhouseMonthMapper.query(map);
        List<FarmhouseUseMonthVO> vos = FarmhouseUseMonthVO.convert(farmhouseDailyPOS);
        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(vos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<FarmhouseUseMonthVO> vos) {
        if(vos==null||vos.size()==0){
            return null;
        }
        String[][] contents=new String[vos.size()][EXPORT_TITLES.length];
        int len=0;
        for(FarmhouseUseMonthVO vo:vos){
            contents[len][0]=vo.getMonth();
            contents[len][1]=vo.getName();
            contents[len][2]=vo.getNumber()+"";
            contents[len][3]=vo.getTimes()+"";
            len++;
        }
        return contents;
    }
}
