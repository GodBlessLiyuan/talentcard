package com.talentcard.common.utils.rabbit;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Application;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Business;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Profile;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/10/15 14:21
 * @description: 将消息放松到mq，然后将消息写入区块链
 * @version: 1.0
 */
@Component
public class BsnRabbitUtil {

    @Autowired
    private AmqpTemplate autoTemplate;


    /**
     * 人才卡记录表
     *
     * @param application
     * @param sync
     */
    public void sendApplication(Application application, boolean sync) {
        // 封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("method", "application");
        data.put("data", JSON.toJSONString(application));
        // 发送消息
        if (sync) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                                                                          @Override
                                                                          public void afterCommit() {
                                                                              // RabbitMQ
                                                                              autoTemplate.convertAndSend("track", data);
                                                                          }
                                                                      }
            );
        } else {
            autoTemplate.convertAndSend("bsn", data);
        }
    }

    /**
     * 人才商业服务记录表
     *
     * @param business
     * @param sync
     */
    public void sendBusiness(Business business, boolean sync) {
        // 封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("method", "business");
        data.put("data", JSON.toJSONString(business));
        // 发送消息
        if (sync) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                                                                          @Override
                                                                          public void afterCommit() {
                                                                              // RabbitMQ
                                                                              autoTemplate.convertAndSend("track", data);
                                                                          }
                                                                      }
            );
        } else {
            autoTemplate.convertAndSend("bsn", data);
        }
    }


    /**
     * 用户申请政策表
     *
     * @param profile
     * @param sync
     */
    public void sendProfile(Profile profile, boolean sync) {
        // 封装数据
        Map<String, Object> data = new HashMap<>();
        data.put("method", "profile");
        data.put("data", JSON.toJSONString(profile));
        // 发送消息
        if (sync) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                                                                          @Override
                                                                          public void afterCommit() {
                                                                              // RabbitMQ
                                                                              autoTemplate.convertAndSend("track", data);
                                                                          }
                                                                      }
            );
        } else {
            autoTemplate.convertAndSend("bsn", data);
        }
    }
}
