package com.talentcard.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.talentcard.common.mapper")
@SpringBootApplication
public class TalentfrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentfrontApplication.class, args);
    }
}
