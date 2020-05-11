package com.talentcard.front.service.impl;

import com.netflix.discovery.converters.Auto;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.UserCurrentInfoMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:24
 * @description
 */
public class TalentTripServiceImpl implements ITalentTripService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Override
    public ResultVO findFirstContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        Long cardId = talentPO.getCardId();
//        Long category = userCurrentInfoPO.getTalentCategory();
//        Long education = userCurrentInfoPO.getEducation();




        return null;
    }
}
