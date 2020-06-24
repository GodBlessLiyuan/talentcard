package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_insert_certification
 * @author 
 */
@Data
public class InsertCertificationPO implements Serializable {
    private Long insertCertId;

    private Long talentId;

    /**
     * 1认证通过
2待审批
3驳回
     */
    private Byte status;

    /**
     * 1 学历
2 职称
3 职业资格
4 人才荣誉
     */
    private Byte type;

    private Date createTime;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private Long certinfo;

    private static final long serialVersionUID = 1L;
}