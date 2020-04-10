package com.talentcard.front.service;

import com.talentcard.common.vo.ResultVO;

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
     * @param tid
     * @return
     */
    ResultVO policies(Long tid);

    /**
     * 我的权益 - 申请
     *
     * @param tid
     * @param pid
     * @return
     */
    ResultVO apply(Long tid, Long pid);

    /**
     * 我的申请
     *
     * @param tid 人才ID
     * @return
     */
    ResultVO applies(Long tid);

    /**
     * 我的申请 - 详情
     *
     * @param paid
     * @return
     */
    ResultVO detail(Long paid);
}
