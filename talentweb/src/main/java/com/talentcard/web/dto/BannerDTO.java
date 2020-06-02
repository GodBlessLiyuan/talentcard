package com.talentcard.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 20:10
 * @description: Banner 配置
 * @version: 1.0
 */
@Data
public class BannerDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String picture;
    /**
     * 跳转地址
     */
    private String jump;
    /**
     * 跳转方式； 1： 小程序内部详情页； 2： H5页面
     */
    private Byte type;
    /**
     * 备注
     */
    private String extra;
}
