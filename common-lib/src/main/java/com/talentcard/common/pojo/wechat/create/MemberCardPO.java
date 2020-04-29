package com.talentcard.common.pojo.wechat.create;

import lombok.Data;

@Data
public class MemberCardPO {
    private String background_pic_url;
    private BaseInfoPO base_info;
    private final Boolean supply_bonus = Boolean.FALSE;
    private final Boolean supply_balance = Boolean.FALSE;
    private String prerogative;
    private final Boolean auto_activate = Boolean.TRUE;
    private final CustomFiled1PO custom_field1 = new CustomFiled1PO();
    private CustomCell1PO custom_cell1;
}
