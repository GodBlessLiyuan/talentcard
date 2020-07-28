package com.talentcard.web.vo;

import com.talentcard.common.pojo.OpwebRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OpwebRecordVO implements Serializable {

    private Long opId;

    private Long useId;

    private String username;

    private String fristMenu;

    private String secondMenu;

    private String thirdMenu;

    private String detailInfo;

    private Date createTime;


    public static ArrayList<OpwebRecordVO> convert(List<OpwebRecordPO> opwebRecordPOs) {
        if(opwebRecordPOs ==null||opwebRecordPOs.size()==0){
            return null;
        }
        ArrayList<OpwebRecordVO> vos = new ArrayList<>(opwebRecordPOs.size());
        for(OpwebRecordPO po:opwebRecordPOs){
            OpwebRecordVO vo = new OpwebRecordVO();
            vo.setOpId(po.getOpId());
            vo.setUseId(po.getUseId());
            vo.setUsername(po.getUsername());
            vo.setFristMenu(po.getFristMenu());
            vo.setSecondMenu(po.getSecondMenu());
            vo.setThirdMenu(po.getThirdMenu());
            vo.setDetailInfo(po.getDetailInfo());
            vo.setCreateTime(po.getCreateTime());
            vos.add(vo);
        }
        return vos;
    }

}
