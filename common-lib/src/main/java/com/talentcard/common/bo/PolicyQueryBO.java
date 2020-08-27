package com.talentcard.common.bo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-14 18:33
 * @description
 */
@Data
@Component
public class PolicyQueryBO {
    //政策id
    private Long policyId;
    //政策名称
    private String policyName;
    //政策编号
    private String num;
    //申请时间开启时间
    private Date startTime;
    //申请时间结束时间
    private Date endTime;
    //政策类型名称：如果下架，则增加（已下架）
    //用于query中的查询结果
    private String policyTypeName;
    //责任单位
    private String roleName;
    //更新时间
    private Date updateTime;
    //上下架
    private Byte upDown;
    //符合条件人数
    private Integer meetConditionNumber;
    //状态
    private Byte status;
    //政策大类上下架
    private Byte policyTypeUpDown;
    //政策类型名称
    private String poTypeName;
    public static List<PolicyQueryBO> setUpStatus(List<PolicyQueryBO> policyQueryBOList) {
        if (policyQueryBOList == null) {
            return null;
        }
        for (PolicyQueryBO policyQueryBO : policyQueryBOList) {
            //判断政策状态
            if (policyQueryBO.getUpDown() == 2) {
                //已下架
                policyQueryBO.setStatus((byte) 4);
            } else {
                Long start = policyQueryBO.getStartTime().getTime();
                Long end = policyQueryBO.getEndTime().getTime();
                Long current = System.currentTimeMillis();
                if (current >= end) {
                    //已失效
                    policyQueryBO.setStatus((byte) 3);
                } else if (current <= start) {
                    //未开启
                    policyQueryBO.setStatus((byte) 2);
                } else if (current >= start && current <= end) {
                    //可申请
                    policyQueryBO.setStatus((byte) 1);
                }
            }
            if(policyQueryBO.getPolicyTypeUpDown()==2){
                policyQueryBO.setPolicyTypeName(policyQueryBO.getPolicyTypeName()+"（已下架）");

            }
        }
        return policyQueryBOList;
    }
}
