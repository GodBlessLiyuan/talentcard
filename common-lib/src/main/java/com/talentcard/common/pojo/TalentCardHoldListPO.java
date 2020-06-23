package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_talent_card_hold_list
 * @author 
 */
@Data
public class TalentCardHoldListPO implements Serializable {
    private Long tchlId;

    private String idCard;

    private Long num;

    private static final long serialVersionUID = 1L;
}