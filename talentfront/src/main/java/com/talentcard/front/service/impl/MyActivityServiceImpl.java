package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.bo.FootprintBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IMyActivityService;
import com.talentcard.front.vo.TalentActivityCollectVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-04 14:33
 * @description
 */
@Service
public class MyActivityServiceImpl implements IMyActivityService {
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private ScenicMapper scenicMapper;
    @Autowired
    private FarmhouseMapper farmhouseMapper;
    @Autowired
    private TalentActivityCollectMapper talentActivityCollectMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;

    @Override
    public ResultVO addFeedBack(String openId, String content, String picture, String contact) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查无此人！");
        }
        FeedbackPO feedbackPO = new FeedbackPO();
        feedbackPO.setTalentId(talentPO.getTalentId());
        feedbackPO.setContent(content);
        feedbackPO.setPicture(picture);
        feedbackPO.setContact(contact);
        feedbackPO.setCreateTime(new Date());
        feedbackMapper.insertSelective(feedbackPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO footprint(String openId) {

        String mapStr = this.redisMapUtil.hget(openId, TalentConstant.TALENT_FOOTPIRNT);
        if(!StringUtils.isEmpty(mapStr)){
            List<FootprintBO> footprintBOList = StringToObjUtil.strToObj(mapStr, List.class);
            if(footprintBOList != null){
                return new ResultVO(1000, footprintBOList);
            }
        }


        List<FootprintBO> footprintBOList = talentActivityHistoryMapper.footprint(openId);
        if (footprintBOList != null) {
            footprintBOList = setFootPrintInfo(footprintBOList);
        }

        this.redisMapUtil.hset(openId, TalentConstant.TALENT_FOOTPIRNT, JSON.toJSONString(footprintBOList));

        return new ResultVO(1000, footprintBOList);
    }

    @Override
    public ResultVO collect(String openId, Long activityFirstContentId,
                            Long activitySecondContentId, Byte ifCollect) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查无此人！");
        }
        Long talentId = talentPO.getTalentId();
        /**
         * 收藏
         */
        if (ifCollect == 1) {
            List<TalentActivityCollectPO> talentActivityCollectPOList
                    = talentActivityCollectMapper.findOne(openId, activityFirstContentId, activitySecondContentId);
            //如果同一个活动数据存在，修正
            if (talentActivityCollectPOList.size() >= 1) {
                talentActivityCollectMapper.deleteByFactor(talentId, activityFirstContentId, activitySecondContentId);
            }
            TalentActivityCollectPO talentActivityCollectPO = new TalentActivityCollectPO();
            talentActivityCollectPO.setActivityFirstContentId(activityFirstContentId);
            talentActivityCollectPO.setActivitySecondContentId(activitySecondContentId);
            talentActivityCollectPO.setCreateTime(new Date());
            talentActivityCollectPO.setStatus((byte) 1);
            talentActivityCollectPO.setTalentId(talentId);
            talentActivityCollectMapper.insertSelective(talentActivityCollectPO);
        } else {
            /**
             * 取消收藏
             */
            talentActivityCollectMapper.deleteByFactor(talentId, activityFirstContentId, activitySecondContentId);
        }

        this.redisMapUtil.hdel(openId, TalentConstant.TALENT_MYCOLLECT);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO findMyCollect(String openId) {

        String mapStr = this.redisMapUtil.hget(openId, TalentConstant.TALENT_MYCOLLECT);
        if(!StringUtils.isEmpty(mapStr)){
            List<TalentActivityCollectVO> talentActivityCollectVOList = StringToObjUtil.strToObj(mapStr,List.class);
            if(talentActivityCollectVOList != null){
                return new ResultVO(1000, talentActivityCollectVOList);
            }
        }

        List<TalentActivityCollectPO> talentActivityCollectPOList = talentActivityCollectMapper.findMyCollect(openId);
        List<TalentActivityCollectVO> talentActivityCollectVOList = TalentActivityCollectVO.convert(talentActivityCollectPOList);

        this.redisMapUtil.hset(openId, TalentConstant.TALENT_MYCOLLECT, JSON.toJSONString(talentActivityCollectVOList));

        return new ResultVO(1000, talentActivityCollectVOList);
    }

    public List<FootprintBO> setFootPrintInfo(List<FootprintBO> footprintBOList) {
        ScenicPO scenicPO;
        FarmhousePO farmhousePO;
        for (FootprintBO footprintBO : footprintBOList) {
            if (footprintBO.getActivityFirstContentId() == 1) {
                scenicPO = scenicMapper.selectByPrimaryKey(footprintBO.getActivitySecondContentId());
                if (scenicPO != null) {
                    footprintBO.setLocation(scenicPO.getLocation());
                    footprintBO.setSubtitle(scenicPO.getSubtitle());
                    footprintBO.setStatlevel(scenicPO.getStarlevel());
                    footprintBO.setIfReady(scenicPO.getStatus());
                    if (scenicPO.getAvatar() != null && !StringUtils.isEmpty(scenicPO.getAvatar())) {
                        footprintBO.setAvatar(FilePathConfig.getStaticPublicBasePath() + scenicPO.getAvatar());
                    }
                }

            } else {
                farmhousePO = farmhouseMapper.selectByPrimaryKey(footprintBO.getActivitySecondContentId());
                if (farmhousePO != null) {
                    footprintBO.setLocation(farmhousePO.getLocation());
                    footprintBO.setSubtitle(farmhousePO.getSubtitle());
                    footprintBO.setIfReady(farmhousePO.getStatus());
                    footprintBO.setDiscount(farmhousePO.getDiscount());
                    if (farmhousePO.getAvatar() != null && !StringUtils.isEmpty(farmhousePO.getAvatar())) {
                        footprintBO.setAvatar(FilePathConfig.getStaticPublicBasePath() + farmhousePO.getAvatar());
                    }
                }

            }
        }
        return footprintBOList;
    }
}
