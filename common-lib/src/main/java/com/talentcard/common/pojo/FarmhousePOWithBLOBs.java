package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_farmhouse
 * @author 
 */
@Data
public class FarmhousePOWithBLOBs extends FarmhousePO implements Serializable {
    private String description;

    private String extra;

    private static final long serialVersionUID = 1L;
}