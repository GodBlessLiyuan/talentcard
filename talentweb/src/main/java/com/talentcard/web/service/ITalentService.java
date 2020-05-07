package com.talentcard.web.service;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.TalentVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:15
 * @description: 人才管理
 * @version: 1.0
 */
public interface ITalentService {
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    PageInfoVO<TalentVO> query(int pageNum, int pageSize, Map<String, Object> reqMap);

    /**
     * 查看详情
     *
     * @param tid
     * @return
     */
    ResultVO detail(Long tid);

    /**
     * 产看认证人才
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    PageInfoVO<TalentVO> queryCert(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO edit(Long talentId, Long cardId);
}
