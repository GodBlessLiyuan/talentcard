package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 10:38
 * @description
 */
public interface IInsertCertificationService {
    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @param hashMap
     * @return
     */
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap);

    /**
     * 审批通过
     * @param talentId
     * @param insertCertId
     * @param result
     * @param opinion
     * @return
     */
    ResultVO certResult(HttpSession httpSession,  Long talentId, Long insertCertId, Byte result, String opinion);

    /**
     * 认证信息详情
     * @param talentId
     * @param insertCertId
     * @return
     */
    ResultVO findOne(Long talentId, Long insertCertId);


}
