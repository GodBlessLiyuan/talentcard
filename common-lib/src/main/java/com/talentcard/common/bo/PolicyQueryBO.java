package com.talentcard.common.bo;

import com.talentcard.common.pojo.PolicyPO;
import lombok.Data;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-14 18:33
 * @description
 */
@Data
public class PolicyQueryBO{
    //政策名称
    private String policyName;
    //申请时间开启时间
    private Date startTime;
    //申请时间结束时间
    private Date endTime;
    //符合条件人数
    private Integer meetConditionNumber;
    //政策类型名称
    private String policyTypeName;
    //责任单位
    private String roleName;
    //更新时间
    private Date updateTime;
    //上下架
    private Byte upLow;
}
