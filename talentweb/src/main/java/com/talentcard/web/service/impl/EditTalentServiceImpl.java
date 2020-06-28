package com.talentcard.web.service.impl;

import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IEditTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(basicInfoDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        talentPO.setCategory(basicInfoDTO.getCategory());
        talentPO.setPolitical(basicInfoDTO.getPolitical());
        talentPO.setSex(basicInfoDTO.getSex());
        talentPO.setName(basicInfoDTO.getName());
        talentPO.setCardType(basicInfoDTO.getCardType());
        talentPO.setIdCard(basicInfoDTO.getIdCard());
        talentPO.setPassport(basicInfoDTO.getPassport());
        talentPO.setDriverCard(basicInfoDTO.getDriverCard());
        talentPO.setWorkLocation(basicInfoDTO.getWorkLocation());
        talentPO.setWorkLocationType(basicInfoDTO.getWorkLocationType());
        talentPO.setIndustry(basicInfoDTO.getIndustry());
        talentPO.setIndustrySecond(basicInfoDTO.getIndustrySecond());
        talentPO.setPhone(basicInfoDTO.getPhone());
        talentPO.setTalentSource(basicInfoDTO.getTalentSource());
        talentMapper.updateByPrimaryKeySelective(talentPO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO editEducation(EducationDTO educationDTO) {
        return null;
    }

    @Override
    public ResultVO editrofQuality(ProfQualityDTO profQualityDTO) {
        return null;
    }

    @Override
    public ResultVO editProfTitle(ProfTitleDTO profTitleDTO) {
        return null;
    }

    @Override
    public ResultVO editTalentHonour(TalentHonourDTO talentHonourDTO) {
        return null;
    }
}
