package com.talentcard.common.bo;

import com.talentcard.common.pojo.CertApprovalPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:39 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Data
public class ApprovalBO {
    /**
     * 人才基础信息
     */
    private String name;
    private Byte sex;
    private String idCard;
    private String phone;
    private Byte political;
    private String industry;
    private String workUnit;
    private String CreateTime;

    /**
     * 人才卡
     */
    private Long cardId;

    /**
     * 学历信息
     */
    private Integer education;
    private String school;
    private String major;
    private String educPic;

    /**
     * 职称信息
     */
    private Integer ptCategory;
    private String ptInfo;
    private String ptPic;

    /**
     * 职业资格信息
     */
    private Integer pqCategory;
    private String pqInfo;


}