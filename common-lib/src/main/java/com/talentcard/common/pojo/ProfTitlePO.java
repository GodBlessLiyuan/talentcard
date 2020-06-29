package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_prof_title
 * @author 
 */
@Data
public class ProfTitlePO implements Serializable {
    private Long ptId;

    private Integer category = 0;

    private String info;

    private String picture;

    private Long certId;

    private Long talentId;

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

    private Byte ifCertificate;

    private static final long serialVersionUID = 1L;
}