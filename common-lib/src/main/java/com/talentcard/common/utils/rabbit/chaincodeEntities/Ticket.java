package com.talentcard.common.utils.rabbit.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/***
 *  政府部门审批发放政策  --智能合约查宿字段
 * */
@Data
public class Ticket {
    @JSONField(name = "type")
    String type;
    @JSONField(name = "ticketUid")
    String uid;
    @JSONField(name = "ticketName")
    String name;

    @JSONField(name = "description")
    String description;

    @JSONField(name = "status")
    String status;
}
