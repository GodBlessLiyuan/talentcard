package com.talentcard.common.utils.redis;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.utils.StringToObjUtil;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class RedisMapUtil {
    private RedisTemplate<String, String> redisTemplate;

    public RedisMapUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加or更新hash的值
     *
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }

    /**
     * 获取hash中field对应的值
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        Object val = redisTemplate.opsForHash().get(key, field);
        return val == null ? null : val.toString();
    }

    /**
     * 获取list
     * @param key
     * @param field
     * @return
     */
    public List hgetList(String key, String field){
        String mapStr = hget(key,field);
        if (!StringUtils.isEmpty(mapStr)) {
            return StringToObjUtil.strToObj(mapStr, List.class);
        }else {
            return null;
        }

    }

    /**
     * 删除hash中field这一对kv
     *
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    // hash 结构的计数

    public long hincr(String key, String field, long value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }


    public void del(String key){
        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 批量获取方式

    public Map<String, String> hgetall(String key) {
        return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
            Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
            if (CollectionUtils.isEmpty(result)) {
                return new HashMap<>(0);
            }

            Map<String, String> ans = new HashMap<>(result.size());
            for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                ans.put(new String(entry.getKey()), new String(entry.getValue()));
            }
            return ans;
        });
    }

    public Map<String, String> hmget(String key, List<String> fields) {
        List<String> result = redisTemplate.<String, String>opsForHash().multiGet(key, fields);
        Map<String, String> ans = new HashMap<>(fields.size());
        int index = 0;
        for (String field : fields) {
            if (result.get(index) == null) {
                continue;
            }
            ans.put(field, result.get(index));
        }
        return ans;
    }


    /**
     * value为列表的场景
     *
     * @param key
     * @param field
     * @return
     */
    public <T> List<T> hGetList(String key, String field, Class<T> obj) {
        Object value = redisTemplate.opsForHash().get(key, field);
        if (value != null) {
            return JSONObject.parseArray(value.toString(), obj);
        } else {
            return new ArrayList<>();
        }
    }

    public <T> void hSetList(String key, String field, List<T> values) {
        String v = JSONObject.toJSONString(values);
        redisTemplate.opsForHash().put(key, field, v);
    }



    public String hgetObj(String key, String method){

        return null;
    }
}
