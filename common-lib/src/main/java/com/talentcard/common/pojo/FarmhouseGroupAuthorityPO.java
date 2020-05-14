package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_farmhouse_group_authority
 * @author 
 */
@Data
public class FarmhouseGroupAuthorityPO implements Serializable {
    private Long fgtId;

    private Long farmhouseId;

    private String authorityCode;

    private static final long serialVersionUID = 1L;
}