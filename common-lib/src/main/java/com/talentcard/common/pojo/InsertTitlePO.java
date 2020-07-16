package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_insert_title
 * @author 
 */
@Data
public class InsertTitlePO implements Serializable {
    private Long insertPtId;

    private Integer category = 0;

    private String info;

    private String picture;

    /**
     * 1认证通过
2待审批
3驳回
     */
    private Byte status;

    private Long insertCertId;

    private String openId;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String picture2;

    private String picture3;

    private static final long serialVersionUID = 1L;
}