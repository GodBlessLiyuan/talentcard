package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.TripRealTimeBO;
import com.talentcard.common.mapper.CardMapper;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.mapper.TalentActivityHistoryMapper;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITripRealtimeService;
import com.talentcard.web.vo.TripRealTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 16:36
 */
@Service
public class TripRealtimeServiceImpl implements ITripRealtimeService {
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private StaffMapper staffMapper;
    private static String[] EXPORT_TITLES={"入院时间","景区名","游客名","福利类型","人才卡","验证员工"};
    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<TripRealTimeBO> page = PageHelper.startPage(pageNum, pageSize);
        List<TripRealTimeBO> tripRealTimeBOS=talentActivityHistoryMapper.query(map);//实时查询
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(),convert(tripRealTimeBOS)));
    }

    @Override
    public ResultVO export(Map<String, Object> map, HttpServletResponse response) {
        List<TripRealTimeBO> tripRealTimeBOS=talentActivityHistoryMapper.query(map);//实时查询
        List<TripRealTimeVO> vos = convert(tripRealTimeBOS);
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(vos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<TripRealTimeVO> vos) {
        if(vos==null||vos.size()==0){
            return null;
        }
        int num=0;
        String[][] content=new String[vos.size()][EXPORT_TITLES.length];
        for(TripRealTimeVO vo:vos){
            content[num][0]= DateUtil.date2Str(vo.getTime(),DateUtil.YMD_HMS);
            content[num][1]=vo.getScenicName();
            content[num][2]=vo.getYoukename();
            content[num][3]=vo.getWelfare();
            content[num][4]=vo.getCardname();
            content[num][5]=vo.getStaffname();
            num++;
        }
        return content;
    }

    private List<TripRealTimeVO> convert(List<TripRealTimeBO> tripRealTimeBOS) {
        if(tripRealTimeBOS==null||tripRealTimeBOS.size()==0){
            return null;
        }
        List<TripRealTimeVO> vos=new ArrayList<>(tripRealTimeBOS.size());
        for(TripRealTimeBO bo:tripRealTimeBOS){
            TripRealTimeVO vo=new TripRealTimeVO();
            vo.setID(bo.getID());
            vo.setTime(bo.getTime());
            vo.setScenicName(bo.getScenicName());
            vo.setYoukename(bo.getYoukename());
            CardPO cardPO = cardMapper.selectByPrimaryKey(bo.getCardId());
            if(cardPO==null){
                vo.setCardname("");
            }else{
                vo.setCardname(cardPO.getTitle()+cardPO.getInitialWord());
            }
            StaffPO staffPO = staffMapper.selectByPrimaryKey(bo.getStaffID());
            if(staffPO==null){
                vo.setStaffname("");
            }else{
                vo.setStaffname(staffPO.getName());
            }
            if(bo.getWelfare()==2){
                vo.setWelfare("免费");
            }else if(bo.getWelfare()==3) {
                vo.setWelfare("折扣");
            }
            vos.add(vo);
        }
        return vos;
    }
}
