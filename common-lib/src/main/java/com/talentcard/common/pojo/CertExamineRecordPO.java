package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_cert_examine_record
 * @author 
 */
@Data
public class CertExamineRecordPO implements Serializable {
    private Long ceaId;

    private Long talentId;

    private Long certId;

    private String name;

    /**
     * 1：男；2：女
     */
    private Byte sex;

    private Integer education;

    private Integer ptCategory;

    private Integer pqCategory;

    private Long honourId;

    /**
     * 1通过；2驳回；3待审批
     */
    private Byte result;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}