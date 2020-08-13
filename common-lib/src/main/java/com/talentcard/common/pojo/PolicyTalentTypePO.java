package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_policy_talent_type
 * @author 
 */
@Data
public class PolicyTalentTypePO implements Serializable {
    /**
     * 自增id
     */
    private Long pTalentTypeId;

    /**
     * 人才标签综合id
     */
    private String talentType;

    /**
     * 人才政策ID
     */
    private Long policyId;

    private static final long serialVersionUID = 1L;
}