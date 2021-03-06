package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_talent_honour
 * @author 
 */
@Data
public class TalentHonourPO implements Serializable {
    private Long thId;

    private Long honourId = 0L;

    private String honourPicture;

    private String info;

    private Long certId;

    private Long talentId;

    /**
     * 1.正常使用
2.注册没领卡（待领卡）
3.发起过认证未审批（待审批）
4.已有基础卡，且审批通过但未领卡（待领卡）
5.基础卡正常使用
9. 基本卡失效
10.其他情况失效
     */
    private Byte status;

    /**
     * 1 已认证；
2 未认证；
8 已认证，且是后来新加入的（编辑或者新增认证）
10 本次不认证
     */
    private Byte ifCertificate;

    private String honourPicture2;

    private String honourPicture3;

    private static final long serialVersionUID = 1L;
}