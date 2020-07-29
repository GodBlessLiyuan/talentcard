package com.talentcard.web.service.impl;

import com.talentcard.common.vo.ResultVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description:人才认证的每天发送消息通知的service
 * @author: liyuan
 * @data 2020-07-29 19:03
 */
@Service
public class TalentCertifDaySendService {
    /***
     * 1、成为普通用户后，两天内没有提交认证信息的，提醒认证
     * 2）老用户---已经注册但是一直没有认证的，如果统一某天晚上8点~10点推送一下。
     * */
    @Scheduled(cron = "${talent_certification.dayTime}",fixedDelay = 1000*60*10)
    public ResultVO daySend(){

    }
}
