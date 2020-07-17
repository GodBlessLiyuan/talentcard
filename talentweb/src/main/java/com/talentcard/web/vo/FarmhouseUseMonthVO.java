package com.talentcard.web.vo;

import com.talentcard.common.pojo.FarmhouseMonthPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-17 18:02
 */
@Data
public class FarmhouseUseMonthVO implements Serializable {
    private String month;
    private String name;
    private Long number;
    private Long times;

    public static List<FarmhouseUseMonthVO> convert(List<FarmhouseMonthPO> farmhouseMonthPOS) {
        if(farmhouseMonthPOS==null||farmhouseMonthPOS.size()==0){
            return null;
        }
        List<FarmhouseUseMonthVO> vos=new ArrayList<>(farmhouseMonthPOS.size());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        for(FarmhouseMonthPO po:farmhouseMonthPOS){
            FarmhouseUseMonthVO vo=new FarmhouseUseMonthVO();
            vo.setMonth(format.format(po.getMonth()));
            vo.setName(po.getName());
            vo.setNumber(po.getNumber());
            vo.setTimes(po.getTimes());
            vos.add(vo);
        }
        return vos;
    }
}
