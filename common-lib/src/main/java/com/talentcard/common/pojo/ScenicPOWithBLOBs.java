package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_scenic
 * @author 
 */
@Data
public class ScenicPOWithBLOBs extends ScenicPO implements Serializable {
    private String description;

    private String extra;

    private static final long serialVersionUID = 1L;
}