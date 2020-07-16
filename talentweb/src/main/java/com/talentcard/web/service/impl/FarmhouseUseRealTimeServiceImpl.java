package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.CardMapper;
import com.talentcard.common.mapper.TalentFarmhouseMapper;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExportUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.bo.FarmHouseUserRealTimeBO;
import com.talentcard.web.service.IFarmhouseUseRealTimeService;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.FarmHouseUserRealTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-15 17:53
 */
@Service
public class FarmhouseUseRealTimeServiceImpl implements IFarmhouseUseRealTimeService {
    @Autowired
    private TalentFarmhouseMapper talentFarmhouseMapper;
    @Autowired
    private CardMapper cardMapper;
    private static final String[] EXPORT_TITLES = {"使用时间", "农家乐名称", "游客名", "人才卡", "验证人员"};
    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<FarmHouseUserRealTimeBO> page = PageHelper.startPage(pageNum, pageSize);
        List<FarmHouseUserRealTimeBO> list=talentFarmhouseMapper.query(map);
        List<CardPO> list1 = cardMapper.queryCardIdName();
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), FarmHouseUserRealTimeVO.convert(list,list1)));
    }

    @Override
    public ResultVO export(Map<String, Object> map, HttpServletResponse response) {
        List<FarmHouseUserRealTimeBO> list=talentFarmhouseMapper.query(map);
        List<CardPO> list1 = cardMapper.queryCardIdName();
        List<FarmHouseUserRealTimeVO> vos = FarmHouseUserRealTimeVO.convert(list, list1);
        //生成Excel表格
        ExportUtil.exportExcel(null, EXPORT_TITLES, this.buildExcelContents(vos), response);
        return new ResultVO(1000);
    }

    private String[][] buildExcelContents(List<FarmHouseUserRealTimeVO> vos) {
        if(vos==null||vos.size()==0){
            return null;
        }
        String[][] contents=new String[vos.size()][EXPORT_TITLES.length];
        int num=0;
        for(FarmHouseUserRealTimeVO vo:vos){
            contents[num][0]= DateUtil.date2Str(vo.getTime(), DateUtil.YMD_HMS);
            contents[num][1]=vo.getName();
            contents[num][2]=vo.getYoukename();
            contents[num][3]=vo.getCardname();
            contents[num][4]=vo.getStaffname();
            num++;
        }
        return contents;
    }


}
