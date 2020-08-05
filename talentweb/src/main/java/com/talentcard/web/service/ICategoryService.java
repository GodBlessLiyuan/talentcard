package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
public interface ICategoryService {
    ResultVO add(String name, String description);

    ResultVO edit(Long categoryId, String description);

    ResultVO upDown(Long categoryId, Byte status);

    ResultVO query(int pageNum, int pageSize, String name, Byte status);

    ResultVO findOne(Long categoryId);
}
