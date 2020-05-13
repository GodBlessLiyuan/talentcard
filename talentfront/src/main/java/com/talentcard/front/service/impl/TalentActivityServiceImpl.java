package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentActivityService;
import com.talentcard.front.utils.TalentActivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 20:04
 * @description
 */
@Service
public class TalentActivityServiceImpl implements ITalentActivityService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private TalentTripMapper talentTripMapper;
    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public ResultVO findFirstContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (talentPO == null || userCurrentInfoPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        Long cardId = talentPO.getCardId();
        ArrayList categoryList = null;
        //拆分人才类别
        if (!(userCurrentInfoPO.getTalentCategory().equals("") && userCurrentInfoPO != null)) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        ArrayList<Long> resultList = new ArrayList();

        //旅游
        List<Long> scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (scenicIdList.size() > 0) {
            resultList.add((long) 1);
        }
        return new ResultVO(1000, resultList);
    }

    @Override
    public ResultVO findHistory(String openId) {

        return new ResultVO(1000, );
    }

    @Override
    public String getOpenId(String cardNum) {
        return userCardMapper.findOpenIdByCardNum(cardNum);
    }
}
