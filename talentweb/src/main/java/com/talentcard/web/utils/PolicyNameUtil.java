package com.talentcard.web.utils;

import com.talentcard.common.pojo.PolicyPO;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-29 12:58
 */
public class PolicyNameUtil {
    public static String getNameNumber(PolicyPO policyPO){
        return policyPO.getName()+"（"+policyPO.getNum()+"）";
    }
}
