package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.dto.ApplyNumCountDTO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 20:33
 * @description: 政策审批
 * @version: 1.0
 */
public interface IPolicyApplyService {
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap);

    /**
     * 导出
     *
     * @param reqMap
     * @return
     */
    void export(HashMap<String, Object> reqMap, HttpServletResponse res);

    /**
     * 审批
     *
     * @param session
     * @param paid    政策申请ID
     * @param status  状态
     * @param opinion 意见
     * @return
     */
    ResultVO approval(HttpSession session, Long paid, Byte status, String opinion, BigDecimal actualFunds);

    /**
     * 详情
     *
     * @param paid
     * @return
     */
    ResultVO detail(Long paid);

    /**
     * 待审批数量
     *
     * @return
     */
    ResultVO count(HttpSession httpSession);

    ResultVO cancel(HttpSession httpSession, Long paId, String opinion);

    /**
     * 人数统计查询
     *
     * @param applyNumCountDTO
     * @return
     */
    ResultVO applyNumCount(ApplyNumCountDTO applyNumCountDTO);
}
