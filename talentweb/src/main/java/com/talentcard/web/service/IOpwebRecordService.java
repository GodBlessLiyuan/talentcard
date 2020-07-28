package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

import java.util.HashMap;

/**
 * @author: wangdaohang
 * @date: Created in 14:22 2020/7/27
 * @version: 1.0.0
 * @description:
 */
public interface IOpwebRecordService {

    /**
     * 查询用户在平台的操作记录
     */
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> reqMap);

}
