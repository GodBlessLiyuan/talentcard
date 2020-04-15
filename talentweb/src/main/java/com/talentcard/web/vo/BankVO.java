package com.talentcard.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 15:11
 * @description: 银行
 * @version: 1.0
 */
@Data
public class BankVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 持卡人
     */
    private String uname;
    /**
     * 银行卡号
     */
    private String bnum;
    /**
     * 开户行号
     */
    private String bname;
}
