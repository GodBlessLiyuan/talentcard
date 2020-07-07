package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentInfoCertificationService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.service.IVerifyTalentPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-07 11:33
 * @description
 */
@Component
public class VerifyTalentPropertyServiceImpl implements IVerifyTalentPropertyService {
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
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;

    @Override
    public Integer verifyEducation(ActivcateBO activcateBO, EducationDTO educationDTO) {
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        String openId = educationDTO.getOpenId();
        /**
         * 判断次数是否到3
         */
        Integer educationInsertCertTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 1);
        Integer educationCertTimes = educationMapper.findAllByCertId(certId).size();
        if ((educationCertTimes + educationInsertCertTimes) >= 3) {
            return 2670;
        }

        /**
         *判断该认证是否重复
         */
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return 2500;
        }
        if (talentCertificationInfoPO.getEducation() != null) {
            if (talentCertificationInfoPO.getEducation().contains(educationDTO.getEducation().toString())) {
                return 2558;
            }
        }
        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, educationDTO.getEducation().longValue(), (byte) 1);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer verifyQuality(ActivcateBO activcateBO, ProfQualityDTO profQualityDTO) {
        String openId = profQualityDTO.getOpenId();
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         */
        Integer qualityInsertCertTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 3);
        Integer qualityCertTimes = profQualityMapper.findAllByCertId(certId).size();
        if ((qualityCertTimes + qualityInsertCertTimes) >= 3) {
            return 2670;
        }
        /**
         *判断该认证是否重复
         */
        //认证后的属性

        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return 2570;
        }
        if (talentCertificationInfoPO.getPqCategory() != null) {
            if (talentCertificationInfoPO.getPqCategory().contains(profQualityDTO.getCategory().toString())) {
                return 2558;
            }
        }

        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profQualityDTO.getCategory().longValue(), (byte) 3);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer verifyTitle(ActivcateBO activcateBO, ProfTitleDTO profTitleDTO) {
        String openId = profTitleDTO.getOpenId();
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         */
        Integer titleInsertCertTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 2);
        Integer titleCertTimes = profTitleMapper.findAllByCertId(certId).size();
        if ((titleCertTimes + titleInsertCertTimes) >= 3) {
            return 2670;
        }
        /**
         *判断该认证是否重复
         */
        //认证后的属性
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return 2500;
        }
        if (talentCertificationInfoPO.getPtCategory() != null) {
            if (talentCertificationInfoPO.getPtCategory().contains(profTitleDTO.getCategory().toString())) {
                return 2558;
            }
        }

        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profTitleDTO.getCategory().longValue(), (byte) 2);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer verifyHonour(ActivcateBO activcateBO, TalentHonourDTO talentHonourDTO) {
        String openId = talentHonourDTO.getOpenId();
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         */
        Integer honourInsertCertTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 4);
        Integer honourCertTimes = talentHonourMapper.findAllByCertId(certId).size();
        if ((honourCertTimes + honourInsertCertTimes) >= 3) {
            return 2670;
        }
        /**
         *判断该认证是否重复
         */
        //认证后的属性

        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return 2500;
        }
        if (talentCertificationInfoPO.getHonourId() != null) {
            if (talentCertificationInfoPO.getHonourId().contains(talentHonourDTO.getHonourId().toString())) {
                return 2558;
            }
        }

        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, talentHonourDTO.getHonourId().longValue(), (byte) 4);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }


    @Override
    public Integer editVerifyEducation(ActivcateBO activcateBO, EducationDTO educationDTO) {
        Long talentId = activcateBO.getTalentId();
        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, educationDTO.getEducation().longValue(), (byte) 1);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer editVerifyQuality(ActivcateBO activcateBO, ProfQualityDTO profQualityDTO) {
        Long talentId = activcateBO.getTalentId();
        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profQualityDTO.getCategory().longValue(), (byte) 3);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer editVerifyTitle(ActivcateBO activcateBO, ProfTitleDTO profTitleDTO) {
        Long talentId = activcateBO.getTalentId();
        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profTitleDTO.getCategory().longValue(), (byte) 2);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

    @Override
    public Integer editVerifyHonour(ActivcateBO activcateBO, TalentHonourDTO talentHonourDTO) {
        Long talentId = activcateBO.getTalentId();
        //新增认证待审批是否重复
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, talentHonourDTO.getHonourId().longValue(), (byte) 4);
        if (ifExistInsertCertification != 0) {
            return 2558;
        }
        return 0;
    }

}
