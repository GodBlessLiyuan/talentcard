package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 10:38
 * @description
 */
public interface IInsertCertificationService {
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap);
}
