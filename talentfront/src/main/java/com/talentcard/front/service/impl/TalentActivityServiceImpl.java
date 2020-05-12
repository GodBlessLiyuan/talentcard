package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.UserCurrentInfoMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentActivityService;
import com.talentcard.front.utils.TalentActivityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cardId", cardId);
        hashMap.put("categoryList", categoryList);
        hashMap.put("education", education);
        hashMap.put("title", title);
        hashMap.put("quality", quality);
        ArrayList<Long> resultList = new ArrayList();

        //旅游
        Integer trip = scenicEnjoyMapper.ifEnableEnjoy(hashMap);
        if (trip > 0) {
            resultList.add((long) 1);
        }
        return new ResultVO(1000, resultList);
    }
}
