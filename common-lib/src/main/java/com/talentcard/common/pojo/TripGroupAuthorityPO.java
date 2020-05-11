package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_trip_group_authority
 * @author 
 */
@Data
public class TripGroupAuthorityPO implements Serializable {
    private Long tgaId;

    private Long scenicId;

    private String authorityCode;

    private static final long serialVersionUID = 1L;
}