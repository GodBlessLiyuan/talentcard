package com.talentcard.web.service;

/**
 * @author: velve
 * @date: Created in 2020/8/21 15:58
 * @description: 微信公众号相关服务
 * @version: 1.0
 */
public interface IWxOfficalAccountService {
    /**
     * 一键推送未申请用户
     * @param openId 人才openId
     * @param policyName 政策名称
     *
     * 示例：
     * 通知示例：
     * 您好，您符合“{{policyName}}”条件，但系统暂未收到您的申请，申请后可享受政策补贴
     * 审批结果：未申请
     * 温馨提醒：申请后可享受政策补贴，请及时申请哦~
     *
     * @return
     */
    int messToNotApply(String openId, String policyName);
}
