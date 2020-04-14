package com.talentcard.web.service;

import com.talentcard.common.utils.DTPageInfo;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.vo.PolicyVO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:22
 * @description: 人才政策管理
 * @version: 1.0
 */
public interface IPolicyService {
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param hashMap
     * @return
     */
    DTPageInfo<PolicyVO> query(int pageNum, int pageSize, HashMap<String, Object> hashMap);

    /**
     * 插入
     *
     * @param session
     * @param dto
     * @return
     */
    ResultVO insert(HttpSession session, PolicyDTO dto);
}
