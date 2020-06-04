package com.talentcard.common.bo;

import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.ScenicPicturePO;
import lombok.Data;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 15:14
 * @description
 */
@Data
public class ScenicBO extends ScenicPO {
    private List<ScenicPicturePO> scenicPicturePOList;

}
