package com.talentcard.common.bo;

import com.talentcard.common.pojo.EvEventLogPO;
import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-02 11:05
 * @description
 */
@Data
public class EvEventLogBO extends EvEventLogPO {
    private String userName;
    private String typeName;
}
