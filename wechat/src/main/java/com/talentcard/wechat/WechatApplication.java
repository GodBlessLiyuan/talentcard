package com.talentcard.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.talentcard.common.mapper")
@MapperScan("com.talentcard.common.utils.redis")
@SpringBootApplication
public class WechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }
}
