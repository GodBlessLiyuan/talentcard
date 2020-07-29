package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:40
 * @description
 */
public interface IStaffService {
    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @param hashMap
     * @return
     */
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap);

    ResultVO delete(HttpSession session,Long staffId);
}
