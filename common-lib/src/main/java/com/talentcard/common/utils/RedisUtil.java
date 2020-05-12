package com.talentcard.common.utils;

import com.talentcard.common.mapper.ConfigMapper;
import com.talentcard.common.pojo.ConfigPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: xiahui
 * @date: Created in 2020/5/12 11:18
 * @description: 缓存工具
 * @version: 1.0
 */
@Component
public class RedisUtil {
    private static StringRedisTemplate template;
    private static ConfigMapper configMapper;

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        RedisUtil.template = template;
    }

    @Autowired
    public void setConfigMapper(ConfigMapper configMapper) {
        RedisUtil.configMapper = configMapper;
    }

    /**
     * 获取 Config 缓存值
     *
     * @param key
     * @return
     */
    public static String getConfigValue(String key) {
        String value = template.opsForValue().get(key);
        if (null == value) {
            ConfigPO po = configMapper.selectByPrimaryKey(key);
            String res = null == po ? "" : po.getConfigValue();
            template.opsForValue().set(key, res);
            return res;
        }

        return value;
    }

    /**
     * 设置 Config 缓存值
     *
     * @param key
     * @param value
     */
    public static void setConfigValue(String key, String value) {
        template.opsForValue().set(key, null == value ? "" : value);
    }
}
