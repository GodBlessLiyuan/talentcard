package com.talentcard.rabbit.component;

import com.talentcard.rabbit.service.ITrackService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2019/11/5 10:53
 * @description: Rabbitmq 消费服务
 * @version: 1.0
 */
@Component
public class RabbitComponent {

    @Autowired
    private ITrackService service;

    /**
     * 追踪队列
     *
     * @param data 数据包
     */
    @RabbitListener(queues = "track")
    public void track(Map<String, Object> data) {
        service.track(data);
    }
}
