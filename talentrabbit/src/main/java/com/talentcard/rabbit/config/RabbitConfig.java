package com.talentcard.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xiahui
 * @date: Created in 2020/10/13 10:32
 * @description: TODO
 * @version: 1.0
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue register() {
        return new Queue("track");
    }
}
