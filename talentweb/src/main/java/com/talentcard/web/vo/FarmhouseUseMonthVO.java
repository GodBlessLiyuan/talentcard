package com.talentcard.web.vo;

import com.talentcard.common.pojo.FarmhouseMonthPO;
import com.talentcard.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-17 18:02
 */
@Data
public class FarmhouseUseMonthVO implements Serializable {
    private Long ID;
    private String month;
    private String name;
    private Long number;
    private Long times;

    public static List<FarmhouseUseMonthVO> convert(List<FarmhouseMonthPO> farmhouseMonthPOS) {
        if(farmhouseMonthPOS==null||farmhouseMonthPOS.size()==0){
            return null;
        }
        List<FarmhouseUseMonthVO> vos=new ArrayList<>(farmhouseMonthPOS.size());

        for(FarmhouseMonthPO po:farmhouseMonthPOS){
            FarmhouseUseMonthVO vo=new FarmhouseUseMonthVO();
            converPO(vo,po);
            vos.add(vo);
        }
        return vos;
    }

    private static void converPO(FarmhouseUseMonthVO vo, FarmhouseMonthPO po) {
        vo.setID(po.getFhM());
        vo.setMonth(DateUtil.date2Str(po.getMonth(),DateUtil.YHM));
        vo.setName(po.getName());
        vo.setNumber(po.getNumber());
        vo.setTimes(po.getTimes());
    }

}
