package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:08 2020/4/15
 * @version: 1.0.0
 * @description: 人才管理的认证审批查询组成实体类
 */
@Data
public class TalentCertStatusBO {
    private String name;
    private Byte sex;
    private Integer education;
    /**
     * 职称类型
     */
    private Integer ptCategory;
    /**
     * 职业资格
     */
    private Integer pqCategory;

    private Byte status;

    private Date createTime;
}
