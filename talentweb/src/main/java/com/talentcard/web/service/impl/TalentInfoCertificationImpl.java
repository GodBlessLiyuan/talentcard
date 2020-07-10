package com.talentcard.web.service.impl;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.TalentCertificationInfoMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.*;
import com.talentcard.web.service.ITalentInfoCertificationService;
import com.talentcard.web.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 认证人才日志表工具类
 *
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-29 19:30
 * @description
 */
@Service
public class TalentInfoCertificationImpl implements ITalentInfoCertificationService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;

    @Override
    public Integer update(Long talentId) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null) {
            return -1;
        }
        String openId = talentPO.getOpenId();
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("openId", openId);
        hashMap.put("status", (byte) 1);
        TalentBO talentBO = talentMapper.findOne(hashMap);
        if (talentBO == null) {
            hashMap.put("status", (byte) 4);
            talentBO = talentMapper.findOne(hashMap);
            if (talentBO == null) {
                return -1;
            }
        }
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return -1;
        }
        //学历
        if (talentBO.getEducationPOList() != null && talentBO.getEducationPOList().size() != 0) {
            String educationString = "";
            List<EducationPO> educationPOList = talentBO.getEducationPOList();
            for (int i = 0; i < educationPOList.size() - 1; i++) {
                educationString = educationString + educationPOList.get(i).getEducation() + ",";
            }
            educationString = educationString + educationPOList.get(educationPOList.size() - 1).getEducation();
            talentCertificationInfoPO.setEducation(educationString);
        } else {
            talentCertificationInfoPO.setEducation("0");
        }
        //职称
        if (talentBO.getProfTitlePOList() != null && talentBO.getProfTitlePOList().size() != 0) {
            String titleString = "";
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            for (int i = 0; i < profTitlePOList.size() - 1; i++) {
                titleString = titleString + profTitlePOList.get(i).getCategory() + ",";
            }
            titleString = titleString + profTitlePOList.get(profTitlePOList.size() - 1).getCategory();
            talentCertificationInfoPO.setPtCategory(titleString);
        } else {
            talentCertificationInfoPO.setPtCategory("0");
        }
        //职业资格
        if (talentBO.getProfQualityPOList() != null && talentBO.getProfQualityPOList().size() != 0) {
            String qualityString = "";
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();
            for (int i = 0; i < profQualityPOList.size() - 1; i++) {
                qualityString = qualityString + profQualityPOList.get(i).getCategory() + ",";
            }
            qualityString = qualityString + profQualityPOList.get(profQualityPOList.size() - 1).getCategory();
            talentCertificationInfoPO.setPqCategory(qualityString);
        } else {
            talentCertificationInfoPO.setPqCategory("0");
        }
        //人才荣誉
        if (talentBO.getTalentHonourPOList() != null && talentBO.getTalentHonourPOList().size() != 0) {
            String honourString = "";
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();
            for (int i = 0; i < talentHonourPOList.size() - 1; i++) {
                honourString = honourString + talentHonourPOList.get(i).getHonourId() + ",";
            }
            honourString = honourString + talentHonourPOList.get(talentHonourPOList.size() - 1).getHonourId();
            talentCertificationInfoPO.setHonourId(honourString);
        } else {
            talentCertificationInfoPO.setHonourId("0");
        }
        //人才类别
        talentCertificationInfoPO.setTalentCategory(talentPO.getCategory());
        talentCertificationInfoMapper.updateByPrimaryKeySelective(talentCertificationInfoPO);
        return 0;
    }
}
