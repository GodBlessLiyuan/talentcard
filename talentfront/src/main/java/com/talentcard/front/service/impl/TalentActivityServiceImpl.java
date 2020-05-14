package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentActivityHistoryPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentActivityService;
import com.talentcard.front.utils.TalentActivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private UserCardMapper userCardMapper;
    @Autowired
    private TalentActivityHistoryMapper talentActivityHistoryMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;

    @Override
    public ResultVO findFirstContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        Long cardId = talentPO.getCardId();
        ArrayList categoryList = null;
        String talentCategory = userCurrentInfoPO.getTalentCategory();
        //拆分人才类别
        if (talentCategory != null && !talentCategory.equals("")) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        ArrayList<Long> resultList = new ArrayList();

        /**
         * 每一个活动挨个枚举
         * todo 加入中间表判断
         */
        List<Long> scenicIdList;
        List<Long> farmhouseList;
        /**
         * 旅游
         */
        scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (scenicIdList.size() > 0) {
            resultList.add((long) 1);
        }
        /**
         * 农家乐
         */
        farmhouseList = farmhouseEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (farmhouseList.size() > 0) {
            resultList.add((long) 2);
        }
        return new ResultVO(1000, resultList);
    }

    @Override
    public ResultVO findHistory(String openId) {
        List<TalentActivityHistoryPO> resultList = talentActivityHistoryMapper.findByOpenId(openId);
        return new ResultVO(1000, resultList);
    }

    @Override
    public String getOpenId(String cardNum) {
        return userCardMapper.findOpenIdByCardNum(cardNum);
    }
}
