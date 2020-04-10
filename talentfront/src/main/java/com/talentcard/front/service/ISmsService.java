package com.talentcard.front.service;

/**
 * @author: ChenXU
 * @date: Created in 2020/04/10 15:25
 * @description: 发送短信模块
 * @version: 1.0
 */
public interface ISmsService {
    int sendSMS(String phone, String verifyCode);
}