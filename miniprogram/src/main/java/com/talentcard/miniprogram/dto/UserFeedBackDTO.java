package com.talentcard.miniprogram.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:10
 */
@Data
public class UserFeedBackDTO implements Serializable {
    private String openId;
    private Byte pageType;
    private Byte relateItem;
    private String proDescribe;
}
