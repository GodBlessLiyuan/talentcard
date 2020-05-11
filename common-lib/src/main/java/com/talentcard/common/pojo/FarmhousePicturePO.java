package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_farmhouse_picture
 * @author 
 */
@Data
public class FarmhousePicturePO implements Serializable {
    private Long fpId;

    private Long farmhouseId;

    private String picture;

    private static final long serialVersionUID = 1L;
}