package com.talentcard.miniprogram.pojo;

import lombok.Data;

@Data
public class JsTokenPO {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}
