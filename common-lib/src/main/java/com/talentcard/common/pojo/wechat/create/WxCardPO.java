package com.talentcard.common.pojo.wechat.create;

import lombok.Data;

@Data
public class WxCardPO {
    private static final String card_type = "MEMBER_CARD";
    private MemberCardPO member_card;
}
