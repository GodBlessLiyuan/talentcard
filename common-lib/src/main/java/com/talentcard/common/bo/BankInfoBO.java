package com.talentcard.common.bo;

import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-20 18:16
 * @description 回填人才上一次申请时填写的银行卡信息
 */
@Data
public class BankInfoBO {
    //持卡人
    private String talentName;
    //卡号
    private String cardNum;
    //开户行名
    private String bankName;
}
