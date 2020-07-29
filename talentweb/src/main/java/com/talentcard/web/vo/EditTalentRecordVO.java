package com.talentcard.web.vo;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.EditTalentRecordBO;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class EditTalentRecordVO {
    private Integer etc_id;

    private String cert_type;

    private Integer operation_content;

    private Integer operation_type;

    private JSONObject before_json;

    private JSONObject after_json;

    private String comment;

    private String user_name;

    private String create_time;

    /**
     * bo 转 vo
     */

    public static ArrayList<EditTalentRecordVO> convert(List<EditTalentRecordBO> editTalentRecordBOS) {
        if (editTalentRecordBOS == null || editTalentRecordBOS.size() == 0) {
            return null;
        }
        ArrayList<EditTalentRecordVO> vos = new ArrayList<>(editTalentRecordBOS.size());
        for (EditTalentRecordBO bo: editTalentRecordBOS) {
            EditTalentRecordVO vo = new EditTalentRecordVO();
            vo.setEtc_id(Integer.valueOf(String.valueOf(bo.getEtcId())));
            vo.setOperation_content(Integer.valueOf(String.valueOf(bo.getOperationContent())));
            vo.setOperation_type(Integer.valueOf(String.valueOf(bo.getOperationType())));
            if(!StringUtils.isEmpty(bo.getBeforeJsonRecord())) {
                vo.setBefore_json(JSONObject.parseObject(bo.getBeforeJsonRecord()));
            }
            if(!StringUtils.isEmpty(bo.getAfterJsonRecord())) {
                vo.setAfter_json(JSONObject.parseObject(bo.getAfterJsonRecord()));
            }
            vo.setComment(bo.getComment());
            vo.setUser_name(bo.getUserName());
            vo.setCreate_time(DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD));

            //设置
            String fir = EditTalentRecordConstant.operationTypeMap.get(bo.getOperationType());
            String sec = EditTalentRecordConstant.operationContentMap.get(bo.getOperationContent());
            vo.setCert_type(fir + sec);
            vos.add(vo);
        }

        return vos;

    }
}
