package com.talentcard.front.service.impl;

import com.talentcard.common.mapper.ScenicEnjoyMapper;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.UserCurrentInfoMapper;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.utils.TalentActivityUtil;
import com.talentcard.front.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:24
 * @description
 */
@Service
public class TalentTripServiceImpl implements ITalentTripService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private ScenicEnjoyMapper scenicEnjoyMapper;
    @Autowired
    private ScenicMapper scenicMapper;

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
        List<Long> scenicIdList = null;
        //景区idList，去大表查询
        scenicIdList = scenicEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
        if (scenicIdList == null) {
            return new ResultVO(2504, "查无景区！");
        }
        scenicIdList = scenicIdList.stream().distinct().collect(Collectors.toList());
        List<ScenicPO> scenicPOList = scenicMapper.findEnjoyScenic(scenicIdList);
        List<ScenicVO> scenicVOList = ScenicVO.convert(scenicPOList);
        HashMap<String, Object> hashMap = new HashMap<>();
        Long benefitNum = ActivityResidueNumUtil.getResidueNum();
        hashMap.put("scenicList", scenicVOList);
        hashMap.put("benefitNum", benefitNum);
        return new ResultVO(1000, hashMap);
    }
}
