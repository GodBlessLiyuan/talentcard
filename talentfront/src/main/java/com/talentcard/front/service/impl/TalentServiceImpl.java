package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.CertificationPO;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        //人才表
        TalentPO talentPO = new TalentPO();
        talentPO.setName(jsonObject.getString("name"));
        talentPO.setSex(jsonObject.getByte("sex"));
        talentPO.setIdCard(jsonObject.getString("idCard"));
        talentPO.setPassport(jsonObject.getString("passport"));
        talentPO.setWorkUnit(jsonObject.getString("workUnit"));
        talentPO.setPhone(jsonObject.getString("phone"));
        talentPO.setCreateTime(new Date());
        talentMapper.add(talentPO);
//        Long talentId = talentPO.getTalentId();

//        //认证表
//        CertificationPO certificationPO = new CertificationPO();
//        certificationPO.setCreateTime(new Date());
//        certificationPO.setStatus((byte)1);
//        certificationPO.setTalentId(talentId);
//        certificationMapper.add(certificationPO);
//        Long certificationId = certificationPO.getCertId();
//
//        //学历表
//        EducationPO educationPO = new EducationPO();
//        educationPO.setEducation(jsonObject.getInteger("education"));
//        educationPO.setSchool(jsonObject.getString("school"));
//        educationPO.setFristClass(jsonObject.getByte("firstClass"));
        return new ResultVO(1000);
    }
}
