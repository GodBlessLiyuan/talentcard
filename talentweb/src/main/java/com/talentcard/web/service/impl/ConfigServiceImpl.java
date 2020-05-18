package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.ConfigMapper;
import com.talentcard.common.pojo.ConfigPO;
import com.talentcard.common.utils.RedisUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IConfigService;
import com.talentcard.web.utils.ActivityResidueNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public ResultVO edit(String key, String value) {

        ConfigPO po = configMapper.selectByPrimaryKey(key);
        if (null == po) {
            po = new ConfigPO();
            po.setConfigKey(key);
            po.setConfigValue(value);
            po.setCreateTime(new Date());
            configMapper.insert(po);
        } else {
            po.setConfigValue(value);
            po.setUpdateTime(new Date());
            configMapper.updateByPrimaryKey(po);
        }

        RedisUtil.setConfigValue(key, value);
        ActivityResidueNumUtil.reviseNum();
        return new ResultVO(1000);
    }
}
