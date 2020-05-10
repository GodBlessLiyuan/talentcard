package com.talentcard.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xiahui
 * @date: Created in 2020/4/28 10:02
 * @description: Spring Boot Admin 监控
 * @version: 1.0
 */
@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableAdminServer
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
