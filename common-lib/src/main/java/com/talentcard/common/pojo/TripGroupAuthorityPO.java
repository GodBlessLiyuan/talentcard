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

    /**
     * 1 旅游
2 农家乐
     */
    private Long activityFirstContentId;

    private Long activitySecondContentId;

    private String authorityCode;

    private static final long serialVersionUID = 1L;
}