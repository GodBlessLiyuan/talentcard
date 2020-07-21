package com.talentcard.web.vo;

import com.talentcard.common.bo.FarmHouseUserRealTimeBO;
import com.talentcard.common.pojo.CardPO;
import lombok.Data;

import java.util.*;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-15 18:38
 */
@Data
public class FarmHouseUserRealTimeVO {
    private String ID;
    private Date time;//使用时间
    private String name;//农家乐名称
    private String youkename;//游客名
    private String cardname;//人才卡
    private String staffname;//验证员工

    public static List<FarmHouseUserRealTimeVO> convert(List<FarmHouseUserRealTimeBO> bos, List<CardPO> cardPOS) {
        if(bos==null||bos.size()==0){
            return null;
        }
        Map<Long,CardPO> map=new HashMap<>();
        if(cardPOS!=null||cardPOS.size()>0){
            for(CardPO cardPO:cardPOS){
                map.put(cardPO.getCardId(),cardPO);
            }
        }
        List<FarmHouseUserRealTimeVO> vos=new ArrayList<>();
        for(FarmHouseUserRealTimeBO bo:bos){
            FarmHouseUserRealTimeVO vo=new FarmHouseUserRealTimeVO();
            vo.setID(bo.getTtId());
            vo.setTime(bo.getTime());
            vo.setName(bo.getName());
            vo.setYoukename(bo.getYoukename());
            vo.setStaffname(bo.getStaffname());
            CardPO cardPO=map.get(bo.getCardID());
            if(cardPO!=null){
                vo.setCardname(cardPO.getTitle()+cardPO.getInitialWord());//卡名拼接
            }else{
                vo.setCardname("");
            }
            vos.add(vo);
        }
        return vos;
    }
}
