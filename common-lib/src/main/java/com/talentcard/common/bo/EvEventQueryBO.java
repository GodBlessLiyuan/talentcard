package com.talentcard.common.bo;

import com.talentcard.common.pojo.EvEventQueryPO;
import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 16:19
 * @description
 */
@Data
public class EvEventQueryBO extends EvEventQueryPO {
    private Byte actualStatus;
}
