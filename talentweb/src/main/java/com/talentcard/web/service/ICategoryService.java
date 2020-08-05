package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
public interface ICategoryService {
    ResultVO add(String name, String description, HttpSession httpSession);

    ResultVO edit(Long categoryId, String description, HttpSession httpSession);

    ResultVO upDown(Long categoryId, Byte status, HttpSession httpSession);

    ResultVO query(int pageNum, int pageSize, String name, Byte status);

    ResultVO findOne(Long categoryId);
}
