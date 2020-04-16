package com.talentcard.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:31 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Data
public class CertStatusVO {
    private Date createTime;
    private String name;
    private byte sex;
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

}
