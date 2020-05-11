package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 16:23
 * @description: 配置
 * @version: 1.0
 */
public interface IConfigService {
    /**
     * 查询配置信息
     *
     * @param key
     * @return
     */
    ResultVO query(String key);

    /**
     * 编辑配置信息
     *
     * @param key
     * @param value
     * @return
     */
    ResultVO edit(String key, String value);
}
