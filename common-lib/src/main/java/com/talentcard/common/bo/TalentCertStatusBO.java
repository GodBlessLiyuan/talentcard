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
    private Long talentId;
    private Long certId;
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

    /**
     * 认证审批表中的结果，1：同意  2：拒绝  null:待审批
     */
    private Byte result;

    private Date createTime;
}
