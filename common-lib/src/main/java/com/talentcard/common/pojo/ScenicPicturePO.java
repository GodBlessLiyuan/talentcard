package com.talentcard.common.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_scenic_picture
 * @author 
 */
@Data
public class ScenicPicturePO implements Serializable {
    private Long spId;

    private Long scenicId;

    private String picture;

    private static final long serialVersionUID = 1L;
}