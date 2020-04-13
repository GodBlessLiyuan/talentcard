package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * 人才注册/认证相关
 *
 * @author ChenXU
 */
@Service
public class TalentServiceImpl implements ITalentService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private CertificationMapper certificationMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;

    @Override
    public ResultVO register(JSONObject jsonObject) {
        //设置状态值 状态3为注册中
        Byte status = (byte) 3;
        //通过currentType判定第一次注册填写的哪一个
        Byte currentType = jsonObject.getByte("currentType");
        //人才表
        TalentPO talentPO = new TalentPO();
        talentPO.setName(jsonObject.getString("name"));
        talentPO.setSex(jsonObject.getByte("sex"));
        talentPO.setIdCard(jsonObject.getString("idCard"));
        talentPO.setPassport(jsonObject.getString("passport"));
        talentPO.setWorkUnit(jsonObject.getString("workUnit"));
        talentPO.setIndustry(jsonObject.getString("industry"));
        talentPO.setPhone(jsonObject.getString("phone"));
        talentPO.setCreateTime(new Date());
        talentMapper.add(talentPO);
        Long talentId = talentPO.getTalentId();

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setCurrentType(currentType);
        certificationMapper.add(certificationPO);
        Long certificationId = certificationPO.getCertId();

        //学历表
        if (currentType == 1) {
            EducationPO educationPO = new EducationPO();
            educationPO.setEducation(jsonObject.getInteger("education"));
            educationPO.setSchool(jsonObject.getString("school"));
            educationPO.setFirstClass(jsonObject.getByte("firstClass"));
            educationPO.setMajor(jsonObject.getString("major"));
            educationPO.setCertId(certificationId);
            educationPO.setTalentId(talentId);
            educationPO.setStatus(status);
            educationMapper.insertSelective(educationPO);
        }

        //职称表
        else if (currentType == 2) {
            ProfTitlePO profTitlePO = new ProfTitlePO();
            profTitlePO.setCategory(jsonObject.getInteger("profTitleCategory"));
            profTitlePO.setInfo(jsonObject.getString("profTitleInfo"));
            profTitlePO.setCertId(certificationId);
            profTitlePO.setTalentId(talentId);
            profTitlePO.setStatus(status);
            profTitleMapper.insertSelective(profTitlePO);
        }

        //职业资格表
        else {
            ProfQualityPO profQualityPO = new ProfQualityPO();
            profQualityPO.setCategory(jsonObject.getInteger("profQualityCategory"));
            profQualityPO.setInfo(jsonObject.getString("profQualityInfo"));
            profQualityPO.setCertId(certificationId);
            profQualityPO.setTalentId(talentId);
            profQualityPO.setStatus(status);
            profQualityMapper.insertSelective(profQualityPO);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(HashMap<String, Object> hashMap) {
        TalentBO talentBO = new TalentBO();
        talentBO = talentMapper.findOne(hashMap).get(0);
        return new ResultVO(1000, talentBO);
    }
}
