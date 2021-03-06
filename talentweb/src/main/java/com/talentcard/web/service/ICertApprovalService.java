package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:30 2020/4/15
 * @version: 1.0.0
 * @description:
 */
public interface ICertApprovalService {
    /**
     * 根据人才姓名查询认证清单信息
     * @param talentId
     * @return
     */
    ResultVO certApprovalShowItems(Long talentId,Long certId);

    /**
     * 查看详情
     * @param talentId
     * @return
     */
    ResultVO detailsLookItems(Long talentId,Long certId);


    /**
     * 根据审批的回馈结果进行各个表更新
     * @param reqData
     * @return
     */
    ResultVO confirmCert(HttpSession session, Map<String, Object> reqData);

    ResultVO queryByNumApproval();

    ResultVO findOne(Long talentId,Long certId);
}
