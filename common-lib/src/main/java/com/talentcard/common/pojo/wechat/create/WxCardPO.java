package com.talentcard.common.pojo.wechat.create;

import lombok.Data;

@Data
public class WxCardPO {
    String card_type;
    MemberCardPO member_card;
}
