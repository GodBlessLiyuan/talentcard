package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_activity_residue_num
 * @author 
 */
@Data
public class ActivityResidueNumPO implements Serializable {
    private Long arnId;

    private Long num;

    private String time;

    private static final long serialVersionUID = 1L;
}