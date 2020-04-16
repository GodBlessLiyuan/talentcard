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
    private final DateInfoPO date_info = new DateInfoPO();
    private final SkuPO sku = new SkuPO();
    private Integer get_limit = 1;
    private final Boolean use_custom_code = Boolean.FALSE;
    private final Boolean can_give_friend = Boolean.FALSE;
    private String center_title;
    private String center_sub_title;
    private String center_url;
    private String custom_url_name;
    private String custom_url;
    private String custom_url_sub_title;
    private String promotion_url_name;
    private String promotion_url;
}
