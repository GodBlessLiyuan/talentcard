package com.talentcard.web.service;

/**
 * @author: velve
 * @date: Created in 2020/8/17 15:35
 * @description: 筛选出最适合用户的政策信息
 * @version: 1.0
 */
public interface IBestPolicyToTalentService {
    /**
     * 异步调用匹配出最适合用户的政策信息
     * @return
     */
    void asynBestPolicy();

    /**
     * 异步调用匹配最适合用户的政策信息
     * @param talentId
     */
    void asynBestPolicyForTalent(Long talentId);
}
