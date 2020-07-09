package com.talentcard.common.bo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 14:59
 * @description
 */
@Data
@Component
public class InsertCertApprovalBO {
    private Date createTime;
    private Long userId;
    private Byte result;
    private Byte type;
    private String opinion;
    private String cTitle;
    private String cInitialWord;
    private String category;
    private String name;
}
