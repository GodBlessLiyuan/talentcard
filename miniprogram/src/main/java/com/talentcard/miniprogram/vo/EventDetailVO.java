package com.talentcard.miniprogram.vo;

import com.talentcard.common.pojo.EvEventPO;
import lombok.Data;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-09-02 15:38
 * @description
 */
@Data
public class EventDetailVO {
    private EvEventPO evEventPO;
    private Byte talentStatus;
    private Long etId;
    private String opinion;
}
