package com.talentcard.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: velve
 * @date: Created in 2020/5/25 14:44
 * @description: TODO
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "vbooster.wx", ignoreUnknownFields = true)
public class WxConfig {
    /**
     * 对外部分接口需要验证token值
     */
    private String checkToken;
}
