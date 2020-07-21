package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.BannerMapper;
import com.talentcard.common.pojo.BannerPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IBannerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-02 10:13
 * @description
 */
@Service
public class BannerServiceImpl implements IBannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;

    @Override
    public ResultVO query() {
        String s_data = this.redisMapUtil.hget(TalentConstant.BANNER_INFO,TalentConstant.BANNER_INFO);
        if(!StringUtils.isEmpty(s_data)){
            List<BannerPO> bannerPOList = StringToObjUtil.strToObj(s_data,List.class);
            if(bannerPOList != null){
                return new ResultVO(1000, bannerPOList);
            }
        }
        List<BannerPO> bannerPOList = bannerMapper.bannerQuery();
        String picture;
        for (BannerPO bannerPO : bannerPOList) {
            picture = bannerPO.getPicture();
            if (picture != null && !StringUtils.isEmpty(picture)) {
                picture = filePathConfig.getPublicBasePath() + picture;
                bannerPO.setPicture(picture);
            }
        }

        this.redisMapUtil.hset(TalentConstant.BANNER_INFO, TalentConstant.BANNER_INFO, JSON.toJSONString(bannerPOList));
        return new ResultVO(1000, bannerPOList);
    }
}
