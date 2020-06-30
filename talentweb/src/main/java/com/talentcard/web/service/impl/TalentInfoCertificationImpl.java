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
            return -1;
        }
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return -1;
        }
        //学历
        if (talentBO.getEducationPOList() != null) {
            String educationString = "";
            List<EducationPO> educationPOList = talentBO.getEducationPOList();
            for (int i = 0; i < educationPOList.size() - 1; i++) {
                educationString = educationPOList.get(i).getEducation() + ",";
            }
            educationString = educationString + educationPOList.get(educationPOList.size());
            talentCertificationInfoPO.setEducation(educationString);
        }
        //职称
        if (talentBO.getProfTitlePOList() != null) {
            String titleString = "";
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            for (int i = 0; i < profTitlePOList.size() - 1; i++) {
                titleString = profTitlePOList.get(i).getCategory() + ",";
            }
            titleString = titleString + profTitlePOList.get(profTitlePOList.size());
            talentCertificationInfoPO.setPtCategory(titleString);
        }
        //职业资格
        if (talentBO.getProfQualityPOList() != null) {
            String qualityString = "";
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();
            for (int i = 0; i < profQualityPOList.size() - 1; i++) {
                qualityString = profQualityPOList.get(i).getCategory() + ",";
            }
            qualityString = qualityString + profQualityPOList.get(profQualityPOList.size());
            talentCertificationInfoPO.setPqCategory(qualityString);
        }
        //人才荣誉
        if (talentBO.getTalentHonourPOList() != null) {
            String honourString = "";
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();
            for (int i = 0; i < talentHonourPOList.size() - 1; i++) {
                honourString = talentHonourPOList.get(i).getHonourId() + ",";
            }
            honourString = honourString + talentHonourPOList.get(talentHonourPOList.size());
            talentCertificationInfoPO.setHonourId(honourString);
        }
        return 0;
    }
}
