package com.talentcard.web.vo;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.EditTalentRecordBO;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.web.utils.BatchCertificateUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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

            //人才类型时，将对应的数据转为成可视文字
            if(bo.getOperationContent() == (byte)EditTalentRecordConstant.talentCategoryContent){
                if(!StringUtils.isEmpty(bo.getBeforeJsonRecord())) {
                    JSONObject jsonObject = JSONObject.parseObject(bo.getBeforeJsonRecord());
//                    String category = jsonObject.getString("category");
//                    jsonObject.put("category",BatchCertificateUtil.convertTalentCategory(category));
                    vo.setBefore_json(jsonObject);
                }
                if(!StringUtils.isEmpty(bo.getAfterJsonRecord())) {
                    JSONObject jsonObject = JSONObject.parseObject(bo.getAfterJsonRecord());
//                    String category = jsonObject.getString("category");
//                    jsonObject.put("category",BatchCertificateUtil.convertTalentCategory(category));
                    vo.setAfter_json(jsonObject);
                }
            }
            //时间戳转为字符串
            else {
                if(!StringUtils.isEmpty(bo.getBeforeJsonRecord())) {
                    JSONObject jsonObject = JSONObject.parseObject(bo.getBeforeJsonRecord());
                    if(jsonObject.containsKey("updateTime")){
                        Long aLong = jsonObject.getLong("updateTime");
                        Date date = new Date(aLong);
                        jsonObject.put("updateTime",DateUtil.date2Str(date,DateUtil.YMD_HMS));
                    }else if(jsonObject.containsKey("createTime")){
                        Long aLong = jsonObject.getLong("createTime");
                        Date date = new Date(aLong);
                        jsonObject.put("createTime",DateUtil.date2Str(date,DateUtil.YMD_HMS));
                    }
                    vo.setBefore_json(jsonObject);
                }
                if(!StringUtils.isEmpty(bo.getAfterJsonRecord())) {
                    JSONObject jsonObject = JSONObject.parseObject(bo.getAfterJsonRecord());
                    if(jsonObject.containsKey("updateTime")){
                        Long aLong = jsonObject.getLong("updateTime");
                        Date date = new Date(aLong);
                        jsonObject.put("updateTime",DateUtil.date2Str(date,DateUtil.YMD_HMS));
                    }else if(jsonObject.containsKey("createTime")){
                        Long aLong = jsonObject.getLong("createTime");
                        Date date = new Date(aLong);
                        jsonObject.put("createTime",DateUtil.date2Str(date,DateUtil.YMD_HMS));
                    }
                    vo.setAfter_json(jsonObject);
                }
            }


            vo.setComment(bo.getComment());
            vo.setUser_name(bo.getUserName());
            vo.setCreate_time(DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD_HMS));

            //设置
            String fir = EditTalentRecordConstant.operationTypeMap.get(bo.getOperationType());
            String sec = EditTalentRecordConstant.operationContentMap.get(bo.getOperationContent());
            vo.setCert_type(fir + sec);
            vos.add(vo);
        }

        return vos;

    }
}
