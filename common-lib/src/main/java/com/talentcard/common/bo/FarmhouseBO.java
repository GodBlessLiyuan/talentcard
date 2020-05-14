package com.talentcard.common.bo;

import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.pojo.FarmhousePicturePO;
import lombok.Data;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-13 18:59
 * @description
 */
@Data
public class FarmhouseBO extends FarmhousePO {
    private List<FarmhousePicturePO> farmhousePicturePOList;
}
