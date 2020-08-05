package com.talentcard.web.vo;

import com.talentcard.common.pojo.OpwebRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OpwebRecordVO implements Serializable {

    private Integer log_id;

    private String user_name;

    private String first_menu;

    private String second_menu;

    private String detail;

    private Date create_time;


    public static ArrayList<OpwebRecordVO> convert(List<OpwebRecordPO> opwebRecordPOs) {
        if (opwebRecordPOs == null || opwebRecordPOs.size() == 0) {
            return null;
        }
        ArrayList<OpwebRecordVO> vos = new ArrayList<>(opwebRecordPOs.size());
        for (OpwebRecordPO po : opwebRecordPOs) {
            OpwebRecordVO vo = new OpwebRecordVO();
            vo.setLog_id(Integer.valueOf(po.getOpId().toString()));
            vo.setUser_name(po.getUsername());
            vo.setFirst_menu(po.getFristMenu());
            vo.setSecond_menu(po.getSecondMenu());
            vo.setDetail(po.getDetailInfo());
            vo.setCreate_time(po.getCreateTime());
            vos.add(vo);
        }
        return vos;
    }

}
