package com.talentcard.common.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_cert_approval_pass_record
 * @author 
 */
@Data
public class CertApprovalPassRecordPO implements Serializable {
    private Long caprId;

    private String talentBoJson;

    private Long certId;

    private Date createTime;

    private Long talentId;

    private static final long serialVersionUID = 1L;
}