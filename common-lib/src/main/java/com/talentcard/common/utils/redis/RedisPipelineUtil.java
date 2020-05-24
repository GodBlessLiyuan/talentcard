package com.talentcard.common.utils.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisPipelineUtil {

    private RedisTemplate<String, String> redisTemplate;

    public RedisPipelineUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void counter(String prefix, String key, String target) {
        // 请注意，返回的结果与内部的redis操作顺序是匹配的
        List<Object> res = redisTemplate.executePipelined(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String mapKey = prefix + "_mp_" + key;
                String cntKey = prefix + "_cnt_" + target;

                redisConnection.openPipeline();
                redisConnection.incr(mapKey.getBytes());
                redisConnection.incr(cntKey.getBytes());
                return null;
            }
        });
        System.out.println(res);
    }

}
