package com.talentcard.common.pojo.wechat.create;

import lombok.Data;

@Data
public class BaseInfoPO {
    private String logo_url;
    private String brand_name;
    private String code_type;
    private String title;
    private String color;
    private String notice;
    private String description;
    private static final DateInfoPO date_info = new DateInfoPO();
    private static final SkuPO sku = new SkuPO();
    private static final Integer get_limit = 1;
    private static final Boolean use_custom_code = Boolean.FALSE;
    private static final Boolean can_give_friend = Boolean.FALSE;
    private String custom_url_name;
    private String custom_url;
    private String custom_url_sub_title;
    private String promotion_url_name;
    private String promotion_url;
}
