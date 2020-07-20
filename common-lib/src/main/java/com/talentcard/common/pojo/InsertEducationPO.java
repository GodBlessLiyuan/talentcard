package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_insert_education
 * @author 
 */
@Data
public class InsertEducationPO implements Serializable {
    private Long insertEducId;

    private Integer education;

    private String school;

    /**
     * 1双一流；2海外人才；3啥也不是
     */
    private Byte firstClass;

    private String major;

    private String educPicture;

    private String openId;

    /**
     * 1认证通过
2待审批
3驳回
     */
    private Byte status;

    private String graduateTime;

    private Long insertCertId;

    /**
     * 1 未删除  2 已删除
     */
    private Byte dr;

    private String educPicture2;

    private String educPicture3;

    private Byte fullTime;

    private static final long serialVersionUID = 1L;
}