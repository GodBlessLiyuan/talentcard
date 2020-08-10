package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.HonourMapper;
import com.talentcard.common.pojo.HonourPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IHonourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
@Service
public class HonourServiceImpl implements IHonourService {
    @Autowired
    HonourMapper honourMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;

    @Override
    public ResultVO query() {
        /**
         * 添加redis hash缓存
         */
        String mapStr = this.redisMapUtil.hget(TalentConstant.TALENT_HONOUR_LIST, TalentConstant.TALENT_HONOUR_LIST);
        List<HonourPO> honourPOList = StringToObjUtil.strToObj(mapStr, List.class);
        if (honourPOList != null) {
            return new ResultVO(1000, honourPOList);
        }
        honourPOList = honourMapper.query(null, null, (byte) 1);
        /**
         * 设置缓存
         */
        this.redisMapUtil.hset(TalentConstant.TALENT_HONOUR_LIST, TalentConstant.TALENT_HONOUR_LIST, JSON.toJSONString(honourPOList));
        return new ResultVO<>(1000, honourPOList);
    }
}
