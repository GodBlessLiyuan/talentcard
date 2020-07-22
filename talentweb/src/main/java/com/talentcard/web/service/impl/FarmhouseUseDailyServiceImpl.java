package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.FarmhouseDailyMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.FarmhouseDailyPO;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.FarmhouseDailyVO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IFarmhouseUseDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
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
    private static final String[] EXPORT_TITLES={"时间","农家乐名称","使用人数","使用次数"};
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO init_daily() {
        //分组得到天数
        List<HashMap<String, String>> updateTimes=talentFarmhouseMapper.groupByUpdateTime();
        if(updateTimes==null||updateTimes.size()==0){
            return new ResultVO(1000,"t_talent_farmhouse表中没有数据");
        }
        List<FarmhouseDailyPO> farmhouseDailyPOS=new ArrayList<>();
        Map<String,String> times=new HashMap(3);
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

    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<FarmhouseDailyPO> page = PageHelper.startPage(pageNum, pageSize);
        List<FarmhouseDailyPO> farmhouseDailyPOS=farmhouseDailyMapper.query(map);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), FarmhouseDailyVO.convert(farmhouseDailyPOS)));
    }

    @Override
    public ResultVO export(Map<String, Object> map, HttpServletResponse response) {
        List<FarmhouseDailyPO> farmhouseDailyPOS = farmhouseDailyMapper.query(map);
        List<FarmhouseDailyVO> vos = FarmhouseDailyVO.convert(farmhouseDailyPOS);
        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(vos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<FarmhouseDailyVO> vos) {
        if(vos==null||vos.size()==0){
            return null;
        }
        String[][] contents=new String[vos.size()][EXPORT_TITLES.length];
        int num=0;
        for(FarmhouseDailyVO vo:vos){
            contents[num][0]=vo.getDailyTime();
            contents[num][1]=vo.getName();
            contents[num][2]=vo.getNumber()+"";
            contents[num][3]=vo.getTimes()+"";
            num++;
        }
        return contents;
    }
}
