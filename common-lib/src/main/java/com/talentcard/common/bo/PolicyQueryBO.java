package com.talentcard.common.bo;

import com.talentcard.common.pojo.PolicyPO;
import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-14 18:33
 * @description
 */
@Data
public class PolicyQueryBO extends PolicyPO {
    //责任单位
    private String roleName;
    //政策类型名称
    private String policyTypeName;
}
