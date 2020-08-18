package com.talentcard.web.service;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-29 19:23
 * @description
 */
public interface ITalentInfoCertificationService {
    /**
     * 更新已经完成认证的用户
     * @param talentId
     */
    Integer update(Long talentId);

    /**
     * 更新用户会员卡信息
     * @param talentId
     * @param cardId
     * @return
     */
    int updateCard(Long talentId, Long cardId);
}
