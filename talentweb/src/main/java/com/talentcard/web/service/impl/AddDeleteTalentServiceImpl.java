package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentHonourPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IAddDeleteTalentService;
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

    @Override
    public ResultVO addEducation(EducationDTO educationDTO) {
        String openId = educationDTO.getOpenId();
        /**
         * 链接学历职称职业资格人才荣誉
         */

        EducationPO educationPO = educationMapper.selectByPrimaryKey(educationDTO.getEducId());
        if (educationPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        educationPO.setGraduateTime(educationDTO.getGraduateTime());
        educationPO.setFirstClass(educationDTO.getFirstClass());
        educationPO.setEducPicture(educationDTO.getEducPicture());
        educationPO.setSchool(educationDTO.getSchool());
        educationPO.setMajor(educationDTO.getMajor());
        educationPO.setEducation(educationDTO.getEducation());
        educationMapper.updateByPrimaryKeySelective(educationPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addProfQuality(ProfQualityDTO profQualityDTO) {
        String openId = profQualityDTO.getOpenId();
        ProfQualityPO profQualityPO = profQualityMapper.selectByPrimaryKey(profQualityDTO.getPqId());
        if (profQualityPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        profQualityPO.setPicture(profQualityDTO.getPicture());
        profQualityPO.setInfo(profQualityDTO.getInfo());
        profQualityPO.setCategory(profQualityDTO.getCategory());
        profQualityMapper.updateByPrimaryKeySelective(profQualityPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addProfTitle(ProfTitleDTO profTitleDTO) {
        String openId = profTitleDTO.getOpenId();
        ProfTitlePO profTitlePO = profTitleMapper.selectByPrimaryKey(profTitleDTO.getInsertTitleId());
        if (profTitlePO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        profTitlePO.setPicture(profTitleDTO.getPicture());
        profTitlePO.setInfo(profTitleDTO.getInfo());
        profTitlePO.setCategory(profTitleDTO.getCategory());
        profTitleMapper.updateByPrimaryKeySelective(profTitlePO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO) {
        String openId = talentHonourDTO.getOpenId();
        TalentHonourPO talentHonourPO = talentHonourMapper.selectByPrimaryKey(talentHonourDTO.getThId());
        if (talentHonourPO == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        talentHonourPO.setInfo(talentHonourDTO.getInfo());
        talentHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        talentHonourPO.setHonourId(talentHonourDTO.getHonourId());
        talentHonourMapper.updateByPrimaryKeySelective(talentHonourPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }


    @Override
    public ResultVO deleteEducation(Long educId) {
        Integer result = educationMapper.deleteByPrimaryKey(educId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfQuality(Long pqId) {
        Integer result = profQualityMapper.deleteByPrimaryKey(pqId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfTitle(Long ptId) {
        Integer result = profTitleMapper.deleteByPrimaryKey(ptId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteTalentHonour(Long thId) {
        Integer result = talentHonourMapper.deleteByPrimaryKey(thId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        return new ResultVO(1000);
    }
}
