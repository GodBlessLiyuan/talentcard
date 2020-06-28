package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IEditTalentService;
import com.talentcard.web.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:18
 * @description
 */
@Service
public class EditTalentServiceImpl implements IEditTalentService {
    @Autowired
    TalentMapper talentMapper;
    @Autowired
    InsertCertificationMapper insertCertificationMapper;
    @Autowired
    InsertEducationMapper insertEducationMapper;
    @Autowired
    InsertQualityMapper insertQualityMapper;
    @Autowired
    InsertTitleMapper insertTitleMapper;
    @Autowired
    InsertHonourMapper insertHonourMapper;
    @Autowired
    InsertCertApprovalMapper insertCertApprovalMapper;
    @Autowired
    private CertificationMapper certificationMapper;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;

    @Autowired
    ITalentService iTalentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(basicInfoDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        talentPO.setPhone(basicInfoDTO.getPhone());
        talentPO.setPolitical(basicInfoDTO.getPolitical());
        talentPO.setIndustry(basicInfoDTO.getIndustry());
        talentPO.setWorkUnit(basicInfoDTO.getWorkUnit());
        talentPO.setWorkLocation(basicInfoDTO.getWorkLocation());
        talentPO.setWorkLocationType(basicInfoDTO.getWorkLocationType());
        talentMapper.updateByPrimaryKeySelective(talentPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editEducation(EducationDTO educationDTO) {
        String openId = educationDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            return new ResultVO(2900, "新增审批时，人才状态不对！");
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        EducationPO educationPO = new EducationPO();
        //学历
        educationPO.setGraduateTime(educationDTO.getGraduateTime());
        educationPO.setFirstClass(educationDTO.getFirstClass());
        educationPO.setEducPicture(educationDTO.getEducPicture());
        educationPO.setSchool(educationDTO.getSchool());
        educationPO.setMajor(educationDTO.getMajor());
        educationPO.setEducation(educationDTO.getEducation());
        educationPO.setIfCertificate((byte) 1);
        educationPO.setCertId(certId);
        educationPO.setStatus((byte) 1);
        educationPO.setTalentId(talentId);
        educationMapper.insertSelective(educationPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfQuality(ProfQualityDTO profQualityDTO) {
        String openId = profQualityDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            return new ResultVO(2900, "新增审批时，人才状态不对！");
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setPicture(profQualityDTO.getPicture());
        profQualityPO.setStatus((byte) 1);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setCertId(certId);
        profQualityPO.setInfo(profQualityDTO.getInfo());
        profQualityPO.setCategory(profQualityDTO.getCategory());
        profQualityPO.setIfCertificate((byte) 1);
        profQualityMapper.insertSelective(profQualityPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfTitle(ProfTitleDTO profTitleDTO) {
        String openId = profTitleDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            return new ResultVO(2900, "新增审批时，人才状态不对！");
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setPicture(profTitleDTO.getPicture());
        profTitlePO.setInfo(profTitleDTO.getInfo());
        profTitlePO.setCategory(profTitleDTO.getCategory());
        profTitlePO.setCertId(certId);
        profTitlePO.setStatus((byte) 1);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setIfCertificate((byte) 1);
        profTitleMapper.insertSelective(profTitlePO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentHonour(TalentHonourDTO talentHonourDTO) {
        String openId = talentHonourDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            return new ResultVO(2900, "新增审批时，人才状态不对！");
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setInfo(talentHonourDTO.getInfo());
        talentHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        talentHonourPO.setHonourId(talentHonourDTO.getHonourId());
        talentHonourPO.setCertId(certId);
        talentHonourPO.setIfCertificate((byte) 1);
        talentHonourPO.setTalentId(talentId);
        talentHonourPO.setStatus((byte) 1);
        talentHonourMapper.insertSelective(talentHonourPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentCategory(String openId, String talentCategory) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if(talentPO==null){
            return new ResultVO(2500);
        }
        talentPO.setCategory(talentCategory);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        userCurrentInfoPO.setTalentCategory(talentCategory);
        userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }
}
