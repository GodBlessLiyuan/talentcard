package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_insert_honour
 * @author 
 */
@Data
public class InsertHonourPO implements Serializable {
    private Long insertThId;

    private Long honourId;

    private String honourPicture;

    private String info;

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

    private Long insertCertId;

    private String openId;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private static final long serialVersionUID = 1L;
}