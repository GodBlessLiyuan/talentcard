package com.talentcard.common.utils.rabbit.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Business {

//| uid         | String | 人才id       |
//| bid | String | 商业活动id |
//| menu    | String | 商业活动目录 |
//| activity    | String | 商业活动名称 |
//| funds       | String | 金额         |
//| create_time | String | 创建时间     |
//| extra | String | 扩展字段 |

    @JSONField(name = "uid")
    String uid;

    @JSONField(name = "bid")
    String bid;

    @JSONField(name = "menu")
    String menu;

    @JSONField(name = "activity")
    String activity;

    @JSONField(name = "funds")
    String funds;

    @JSONField(name = "create_time")
    String create_time;

    @JSONField(name = "extra")
    String extra;
}
