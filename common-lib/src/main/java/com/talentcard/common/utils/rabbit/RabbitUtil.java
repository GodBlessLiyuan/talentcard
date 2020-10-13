package com.talentcard.common.utils.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/10/13 18:21
 * @description: 消息队列工具
 * @version: 1.0
 */
@Component
public class RabbitUtil {
    @Autowired
    private AmqpTemplate autoTemplate;

    private static AmqpTemplate template;

    @PostConstruct
    public void init() {
        template = autoTemplate;
    }

    public static void sendTrackMsg(Byte type, Byte node, String content) {
        sendTrackMsg(type, node, content, false);
    }

    /**
     * 发送追踪消息
     *
     * @param type    追踪类型
     * @param node    追踪节点
     * @param content 追踪内容
     * @param sync    是否同步操作
     */
    public static void sendTrackMsg(Byte type, Byte node, String content, Boolean sync) {
        // 封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("time", new Date());
        data.put("type", type);
        data.put("node", node);
        data.put("content", content);

        // 发送消息
        if (sync) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                                                                          @Override
                                                                          public void afterCommit() {
                                                                              // RabbitMQ
                                                                              template.convertAndSend("track", data);
                                                                          }
                                                                      }
            );
        } else {
            template.convertAndSend("track", data);
        }
    }
}
