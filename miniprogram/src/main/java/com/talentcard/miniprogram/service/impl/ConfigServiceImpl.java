package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.mapper.ConfigMapper;
import com.talentcard.common.utils.RedisUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 16:23
 * @description: 配置
 * @version: 1.0
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Override
    public ResultVO query(String key) {
//        ConfigPO po = configMapper.selectByPrimaryKey(key);
        return new ResultVO<>(1000, RedisUtil.getConfigValue(key));
    }
}
