package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.UserCurrentInfoMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.TalentActivityUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


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
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;

    @Override
    public ResultVO findSecondContent(String openId) {
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

        //景区idList
        List<Long> scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (scenicIdList.size() > 0) {
            resultList.add((long) 1);
        }
        return new ResultVO(1000, resultList);
    }
}
