package com.talentcard.front.service.impl;

import com.netflix.discovery.converters.Auto;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.FeedbackMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.FeedbackPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IMyActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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

    @Override
    public ResultVO addFeedBack(String openId, String content, MultipartFile file, String contact) {
        String picture = "";
        if (file != null) {
            picture = FileUtil.uploadFile
                    (file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getActivityFeedBack(), "feedBack");
        }
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
}
