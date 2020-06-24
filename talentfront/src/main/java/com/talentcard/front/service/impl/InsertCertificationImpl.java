package com.talentcard.front.service.impl;

import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IInsertCertificationService;
import com.talentcard.front.vo.InsertCertificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 10:03
 * @description
 */
@Service
public class InsertCertificationImpl implements IInsertCertificationService {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addEducation(EducationDTO educationDTO) {
        Long insertEducId = educationDTO.getInsertEducId();
        InsertEducationPO insertEducationPO;
        Boolean ifInsert = Boolean.TRUE;
        TalentPO talentPO = talentMapper.selectByOpenId(educationDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        /**
         * 判断新增还是编辑
         */
        if (insertEducId == null) {
            /**
             * 新增
             */
            insertEducationPO = new InsertEducationPO();
            //新增认证表
            InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
            insertCertificationPO.setCreateTime(new Date());
            insertCertificationPO.setStatus((byte) 2);
            insertCertificationPO.setType((byte) 1);
            insertCertificationPO.setTalentId(talentPO.getTalentId());
            insertCertificationMapper.add(insertCertificationPO);
            insertEducationPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        } else {
            /**
             * 编辑
             */
            insertEducationPO = insertEducationMapper.selectByPrimaryKey(insertEducId);
            if (insertEducationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (insertEducationPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            ifInsert = Boolean.FALSE;

        }
        insertEducationPO.setEducation(educationDTO.getEducation());
        insertEducationPO.setEducPicture(educationDTO.getEducPicture());
        insertEducationPO.setFirstClass(educationDTO.getFirstClass());
        insertEducationPO.setGraduateTime(educationDTO.getGraduateTime());
        insertEducationPO.setMajor(educationDTO.getMajor());
        insertEducationPO.setSchool(educationDTO.getSchool());
        insertEducationPO.setStatus((byte) 2);
        insertEducationPO.setOpenId(educationDTO.getOpenId());
        if (ifInsert.equals(Boolean.TRUE)) {
            //新增
            insertEducationMapper.insertSelective(insertEducationPO);
        } else {
            insertEducationMapper.updateByPrimaryKeySelective(insertEducationPO);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addProfQuality(ProfQualityDTO profQualityDTO) {
        Long insertQualityId = profQualityDTO.getInsertQualityId();
        InsertQualityPO insertQualityPO;
        Boolean ifInsert = Boolean.TRUE;
        TalentPO talentPO = talentMapper.selectByOpenId(profQualityDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        /**
         * 判断新增还是编辑
         */
        if (insertQualityId == null) {
            /**
             * 新增
             */
            insertQualityPO = new InsertQualityPO();
            //新增认证表
            InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
            insertCertificationPO.setCreateTime(new Date());
            insertCertificationPO.setStatus((byte) 2);
            insertCertificationPO.setType((byte) 1);
            insertCertificationPO.setTalentId(talentPO.getTalentId());
            insertCertificationMapper.add(insertCertificationPO);
            insertQualityPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        } else {
            /**
             * 编辑
             */
            insertQualityPO = insertQualityMapper.selectByPrimaryKey(insertQualityId);
            if (insertQualityPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (insertQualityPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            ifInsert = Boolean.FALSE;

        }
        insertQualityPO.setCategory(profQualityDTO.getCategory());
        insertQualityPO.setInfo(profQualityDTO.getInfo());
        insertQualityPO.setOpenId(profQualityDTO.getOpenId());
        insertQualityPO.setPicture(profQualityDTO.getPicture());
        insertQualityPO.setStatus((byte) 2);
        if (ifInsert.equals(Boolean.TRUE)) {
            //新增
            insertQualityMapper.insertSelective(insertQualityPO);
        } else {
            insertQualityMapper.updateByPrimaryKeySelective(insertQualityPO);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addProfTitle(ProfTitleDTO profTitleDTO) {
        Long insertTitleId = profTitleDTO.getInsertTitleId();
        InsertTitlePO insertTitlePO;
        Boolean ifInsert = Boolean.TRUE;
        TalentPO talentPO = talentMapper.selectByOpenId(profTitleDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        /**
         * 判断新增还是编辑
         */
        if (insertTitleId == null) {
            /**
             * 新增
             */
            insertTitlePO = new InsertTitlePO();
            //新增认证表
            InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
            insertCertificationPO.setCreateTime(new Date());
            insertCertificationPO.setStatus((byte) 2);
            insertCertificationPO.setType((byte) 1);
            insertCertificationPO.setTalentId(talentPO.getTalentId());
            insertCertificationMapper.add(insertCertificationPO);
            insertTitlePO.setInsertCertId(insertCertificationPO.getInsertCertId());
        } else {
            /**
             * 编辑
             */
            insertTitlePO = insertTitleMapper.selectByPrimaryKey(insertTitleId);
            if (insertTitlePO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (insertTitlePO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            ifInsert = Boolean.FALSE;

        }
        insertTitlePO.setCategory(profTitleDTO.getCategory());
        insertTitlePO.setInfo(profTitleDTO.getInfo());
        insertTitlePO.setOpenId(profTitleDTO.getOpenId());
        insertTitlePO.setPicture(profTitleDTO.getPicture());
        insertTitlePO.setStatus((byte) 2);
        if (ifInsert.equals(Boolean.TRUE)) {
            //新增
            insertTitleMapper.insertSelective(insertTitlePO);
        } else {
            insertTitleMapper.updateByPrimaryKeySelective(insertTitlePO);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO) {
        Long insertTalentHonourId = talentHonourDTO.getInsertTalentHonourId();
        InsertHonourPO insertHonourPO;
        Boolean ifInsert = Boolean.TRUE;
        TalentPO talentPO = talentMapper.selectByOpenId(talentHonourDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        /**
         * 判断新增还是编辑
         */
        if (insertTalentHonourId == null) {
            /**
             * 新增
             */
            insertHonourPO = new InsertHonourPO();
            //新增认证表
            InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
            insertCertificationPO.setCreateTime(new Date());
            insertCertificationPO.setStatus((byte) 2);
            insertCertificationPO.setType((byte) 1);
            insertCertificationPO.setTalentId(talentPO.getTalentId());
            insertCertificationMapper.add(insertCertificationPO);
            insertHonourPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        } else {
            /**
             * 编辑
             */
            insertHonourPO = insertHonourMapper.selectByPrimaryKey(insertTalentHonourId);
            if (insertHonourPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (insertHonourPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            ifInsert = Boolean.FALSE;

        }
        insertHonourPO.setHonourId(talentHonourDTO.getHonourId());
        insertHonourPO.setInfo(talentHonourDTO.getInfo());
        insertHonourPO.setOpenId(talentHonourDTO.getOpenId());
        insertHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        insertHonourPO.setStatus((byte) 2);
        if (ifInsert.equals(Boolean.TRUE)) {
            //新增
            insertHonourMapper.insertSelective(insertHonourPO);
        } else {
            insertHonourMapper.updateByPrimaryKeySelective(insertHonourPO);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(Long insertCertId) {
        InsertCertificationPO insertCertificationPO = insertCertificationMapper.selectByPrimaryKey(insertCertId);
        if (insertCertificationPO == null) {
            if (insertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
        }
        insertCertificationPO.setDr((byte) 2);
        insertCertificationMapper.updateByPrimaryKeySelective(insertCertificationPO);
        Byte type = insertCertificationPO.getType();
        if (type == 1) {
            //学历
            InsertEducationPO insertEducationPO = insertEducationMapper.selectByInsertCertId(insertCertId);
            if (insertEducationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertEducationPO.setStatus((byte) 2);
            insertEducationMapper.updateByPrimaryKeySelective(insertEducationPO);
        } else if (type == 2) {
            //职称
            InsertTitlePO insertTitlePO = insertTitleMapper.selectByInsertCertId(insertCertId);
            if (insertTitlePO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertTitlePO.setStatus((byte) 2);
            insertTitleMapper.updateByPrimaryKeySelective(insertTitlePO);
        } else if (type == 3) {
            //职业资格
            InsertQualityPO insertQualityPO = insertQualityMapper.selectByInsertCertId(insertCertId);
            if (insertQualityPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertQualityPO.setStatus((byte) 2);
            insertQualityMapper.updateByPrimaryKeySelective(insertQualityPO);

        } else {
            //人才荣誉
            InsertHonourPO insertHonourPO = insertHonourMapper.selectByInsertCertId(insertCertId);
            if (insertHonourPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertHonourPO.setStatus((byte) 2);
            insertHonourMapper.updateByPrimaryKeySelective(insertHonourPO);

        }
        return new ResultVO(1000);
    }

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
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOneDetail(String openId, Long insertCertificationId) {
        InsertCertificationBO insertCertificationBO = insertCertificationMapper.findOne(openId, insertCertificationId);
        if (insertCertificationBO == null) {
            return new ResultVO(2551);
        }
        InsertCertificationVO insertCertificationVO = InsertCertificationVO.convert(insertCertificationBO);
        Byte type = insertCertificationVO.getType();
        if (type == null) {
            return new ResultVO(2553, "新增认证type错误为null!");
        }
        Integer currentCertificationTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, type);
        insertCertificationVO.setCurrentCertificationTimes(currentCertificationTimes);
        return new ResultVO(1000, insertCertificationVO);
    }
}
