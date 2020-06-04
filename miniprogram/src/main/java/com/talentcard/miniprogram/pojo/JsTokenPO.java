package com.talentcard.miniprogram.pojo;

import lombok.Data;

@Data
public class JsTokenPO {
    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
