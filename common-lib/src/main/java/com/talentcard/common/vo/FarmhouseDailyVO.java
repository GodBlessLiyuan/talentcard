package com.talentcard.common.vo;

import com.talentcard.common.pojo.FarmhouseDailyPO;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-16 19:43
 */
@Data
public class FarmhouseDailyVO implements Serializable {
    private Long ID;
    private String dailyTime;
    private String name;
    private Long number;
    private Long times;

    public static List<FarmhouseDailyVO> convert(List<FarmhouseDailyPO> farmhouseDailyPOS) {
        if(farmhouseDailyPOS==null||farmhouseDailyPOS.size()==0){
            return null;
        }
        List<FarmhouseDailyVO> vos=new ArrayList<>(farmhouseDailyPOS.size());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        for(FarmhouseDailyPO po:farmhouseDailyPOS){
            FarmhouseDailyVO vo=new FarmhouseDailyVO();
            vo.setID(po.getFhD());//改行的主键
            vo.setDailyTime(format.format(po.getDailyTime()));
            vo.setName(po.getName());
            vo.setNumber(po.getNumber());
            vo.setTimes(po.getTimes());
            vos.add(vo);
        }
        return vos;
    }
}
