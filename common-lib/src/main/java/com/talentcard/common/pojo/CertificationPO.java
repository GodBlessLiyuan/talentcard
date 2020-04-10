package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_certification
 * @author 
 */
@Data
public class CertificationPO implements Serializable {
    private Long certId;

    private Long talentId;

    private String political;

    private Date createTime;

    /**
     * 1 刚注册 2：已同意使用中；3：待审批；4：已驳回 5废弃
     */
    private Byte status;

    /**
     *1 学历 2 职称 3 职业资格 4 全都有
     */
    private Byte currentType;

    private static final long serialVersionUID = 1L;
}