package com.talentcard.web.service;

import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:22 2020/4/15
 * @version: 1.0.0
 * @description:
 */
public interface ICertService {

    /**
     * 查询人才的审核状态信息
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    ResultVO queryCertStatus(int pageNum, int pageSize,Map<String, Object> map);
}
