package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_certification
 * @author 
 */
@Data
public class CertificationPO implements Serializable {
    private Long certId;

    private Long talentId;

    private Byte political;

    private Date createTime;

    /**
     * 1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效
     */
    private Byte status;

    /**
     * 1 学历
2 职称
3 职业资格
4 全都有
     */
    private Byte currentType;

    private Byte type;

    private static final long serialVersionUID = 1L;
}