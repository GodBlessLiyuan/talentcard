package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IAddDeleteTalentService;
import com.talentcard.web.service.ITalentInfoCertificationService;
import com.talentcard.web.service.ITalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-29 09:26
 * @description
 */
@Service
public class AddDeleteTalentServiceImpl implements IAddDeleteTalentService {
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
    private ITalentService iTalentService;
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;

    @Override
    public ResultVO addEducation(EducationDTO educationDTO) {
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
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);

    }

    @Override
    public ResultVO addProfQuality(ProfQualityDTO profQualityDTO) {
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
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addProfTitle(ProfTitleDTO profTitleDTO) {
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
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO) {
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
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }


    @Override
    public ResultVO deleteEducation(String openId, Long educId) {
        Integer result = educationMapper.deleteByPrimaryKey(educId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfQuality(String openId, Long pqId) {
        Integer result = profQualityMapper.deleteByPrimaryKey(pqId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfTitle(String openId, Long ptId) {
        Integer result = profTitleMapper.deleteByPrimaryKey(ptId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteTalentHonour(String openId, Long thId) {
        Integer result = talentHonourMapper.deleteByPrimaryKey(thId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }
}
