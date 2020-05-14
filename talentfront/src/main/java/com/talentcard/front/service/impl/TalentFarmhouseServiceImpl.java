package com.talentcard.front.service.impl;

import com.talentcard.common.bo.FarmhouseBO;
import com.talentcard.common.bo.ScenicBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentFarmhouseService;
import com.talentcard.front.service.ITalentTripService;
import com.talentcard.front.utils.ActivityResidueNumUtil;
import com.talentcard.front.utils.TalentActivityUtil;
import com.talentcard.front.vo.FarmhouseVO;
import com.talentcard.front.vo.ScenicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 18:24
 * @description
 */
@Service
public class TalentFarmhouseServiceImpl implements ITalentFarmhouseService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private FarmhouseEnjoyMapper farmhouseEnjoyMapper;
    @Autowired
    private FarmhouseMapper farmhouseMapper;
    @Autowired
    private FarmhouseGroupAuthorityMapper farmhouseGroupAuthorityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO findSecondContent(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return new ResultVO(2500, "查找当前人才所属福利一级目录：查无此人");
        }
        Long cardId = talentPO.getCardId();
        String category = userCurrentInfoPO.getTalentCategory();
        ArrayList categoryList = null;
        //拆分人才类别
        if (category != null && !category.equals("")) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        List<Long> farmhouseIdList;
        /**
         * 农家乐idList，去中间表查询
         */
        String code = getMiddleTableString(cardId, category, education, title, quality);
        farmhouseIdList = farmhouseGroupAuthorityMapper.findByCode(code);
        /**
         *  中间表没找到景区idList，去大表查询
         */
        if (farmhouseIdList.size() == 0) {
            farmhouseIdList = farmhouseEnjoyMapper.findSecondContent(cardId, categoryList, education, title, quality);
            if (farmhouseIdList.size() == 0) {
                return new ResultVO(2504, "查无景区！");
            }
            //新增中间表
            FarmhouseGroupAuthorityPO farmhouseGroupAuthorityPO = new FarmhouseGroupAuthorityPO();
            farmhouseGroupAuthorityPO.setAuthorityCode(code);
            for (Long farmhouseId : farmhouseIdList) {
                farmhouseGroupAuthorityPO.setFarmhouseId(farmhouseId);
                farmhouseGroupAuthorityMapper.insertSelective(farmhouseGroupAuthorityPO);
            }
        }

        //去重
        farmhouseIdList = farmhouseIdList.stream().distinct().collect(Collectors.toList());
        //景区表，查询符合条件的景区
        List<FarmhousePO> farmhousePOList = farmhouseMapper.findEnjoyFarmhouse(farmhouseIdList);
        List<FarmhouseVO> farmhouseVOList = FarmhouseVO.convert(farmhousePOList);
        //拼结果
        HashMap<String, Object> hashMap = new HashMap<>();
        Long benefitNum = ActivityResidueNumUtil.getResidueNum();
        hashMap.put("farmhouseVOList", farmhouseVOList);
        hashMap.put("benefitNum", benefitNum);
        return new ResultVO(1000, hashMap);
    }

    @Override
    public ResultVO findOne(Long activitySecondContentId) {
        FarmhouseBO farmhouseBO = farmhouseMapper.findOne(activitySecondContentId);
        if (farmhouseBO == null) {
            return new ResultVO(2504, "查无农家乐");
        }
        FarmhouseVO farmhouseVO = FarmhouseVO.convert(farmhouseBO);
        return new ResultVO(1000, farmhouseVO);
    }

    /**
     * 根据五个条件获得中间表唯一字符串
     *
     * @param cardId
     * @param category
     * @param education
     * @param title
     * @param quality
     * @return
     */
    public static String getMiddleTableString(Long cardId, String category,
                                              Integer education, Integer title, Integer quality) {
        String middleTableString;
        if (cardId == null) {
            cardId = (long) 0;
        }
        if (category == null || category.equals("")) {
            category = "0";
        }
        if (education == null) {
            education = 0;
        }
        if (title == null) {
            title = 0;
        }
        if (quality == null) {
            quality = 0;
        }
        middleTableString = "" + cardId + "-" + category + "-" + education + "-" + title + "-" + quality;
        return middleTableString;
    }
}
