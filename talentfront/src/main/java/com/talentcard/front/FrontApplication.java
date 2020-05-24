package com.talentcard.front;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.config.RedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@MapperScan("com.talentcard.common.mapper")
@ComponentScan("com.talentcard.common.utils")
@ComponentScan("com.talentcard.front")
@EnableConfigurationProperties({FilePathConfig.class})
@SpringBootApplication
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}
