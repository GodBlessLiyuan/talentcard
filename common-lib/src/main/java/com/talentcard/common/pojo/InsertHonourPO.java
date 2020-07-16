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

    private Long honourId = 0L;

    private String honourPicture;

    private String info;

    /**
     * 1 认证通过
2 待审批
3 驳回
4 已废弃
     */
    private Byte status;

    private Long insertCertId;

    private String openId;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String honourPicture2;

    private String honourPicture3;

    private static final long serialVersionUID = 1L;
}