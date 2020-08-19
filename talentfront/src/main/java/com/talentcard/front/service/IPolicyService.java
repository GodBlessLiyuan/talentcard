package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.PolicyApplyDTO;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 16:54
 * @description: 政策权益
 * @version: 1.0
 */
public interface IPolicyService {

    /**
     * 我的权益
     *
     * @param openid
     * @return
     */
    ResultVO policies(String openid);

    /**
     * 我的权益 - 申请
     *
     * @param dto
     * @return
     */
    ResultVO apply(PolicyApplyDTO dto);

    /**
     * 我的申请
     *
     * @param openid 人才ID
     * @return
     */
    ResultVO applies(String openid);

    /**
     * 我的申请 - 详情
     *
     * @param paid
     * @return
     */
    ResultVO detail(Long paid);

    /**
     * 根據openId獲取銀行卡信息
     * @param openId
     * @return
     */
    ResultVO queryBankCardInfo(String openId);


    /**
     * 根据policyId，获取指定政策信息
     * @param policyId
     * @return
     */
    ResultVO policyFindOne(Long policyId);
}
