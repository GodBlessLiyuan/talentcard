package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 8:57 2020/10/23
 * @ Description：政策审批服务类
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IPolicyApprovalService {
    /**
     * 分页查询
     *
     * @param reqMap
     * @param session
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqMap, HttpSession session);

    /**
     * 分角色查询未审批数量
     *
     * @param session
     * @param session
     * @return
     */
    ResultVO notApprovalNum(HttpSession session);
}
