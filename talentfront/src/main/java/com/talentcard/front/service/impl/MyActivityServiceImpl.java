package com.talentcard.front.service.impl;

import com.netflix.discovery.converters.Auto;
import com.talentcard.common.bo.FootprintBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IMyActivityService;
import com.talentcard.front.vo.TalentActivityCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
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
    private UserCardMapper userCardMapper;
    @Autowired
    private TalentActivityCollectMapper talentActivityCollectMapper;

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
        List<FootprintBO> footprintBOList = talentActivityHistoryMapper.footprint(openId);
        if (footprintBOList != null) {
            footprintBOList = setFootPrintInfo(footprintBOList);
        }
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
        List<TalentActivityCollectPO> talentActivityCollectPOList
                = talentActivityCollectMapper.findOne(openId, activityFirstContentId, activitySecondContentId);
        /**
         * 收藏
         */
        if (ifCollect == 1) {
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
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findMyCollect(String openId) {
        List<TalentActivityCollectPO> talentActivityCollectPOList = talentActivityCollectMapper.findMyCollect(openId);
        List<TalentActivityCollectVO> talentActivityCollectVOList = TalentActivityCollectVO.convert(talentActivityCollectPOList);
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
                    scenicPO = null;
                }

            } else {
                farmhousePO = farmhouseMapper.selectByPrimaryKey(footprintBO.getActivitySecondContentId());
                if (farmhousePO != null) {
                    footprintBO.setLocation(farmhousePO.getLocation());
                    footprintBO.setSubtitle(farmhousePO.getSubtitle());
                    farmhousePO = null;
                }

            }
        }
        return footprintBOList;
    }
}
