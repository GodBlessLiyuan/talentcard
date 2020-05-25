package com.talentcard.wechat;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.config.RedisConfig;
import com.talentcard.wechat.config.WxConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@AutoConfigurationPackage
@MapperScan("com.talentcard.common.mapper")
@ComponentScan("com.talentcard.common.utils")
@ComponentScan("com.talentcard.wechat")
@SpringBootApplication
@EnableConfigurationProperties({WxConfig.class})
public class WechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }
}
