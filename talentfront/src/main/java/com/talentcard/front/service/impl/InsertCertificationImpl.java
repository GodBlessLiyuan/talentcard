package com.talentcard.front.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.constant.InsertCertificationConstant;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IInsertCertificationService;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.vo.InsertCertificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

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
    @Autowired
    InsertCertApprovalMapper insertCertApprovalMapper;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    ITalentService iTalentService;
    @Autowired
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addEducation(EducationDTO educationDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(educationDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         *判断该认证是否重复
         */
        Long talentId = talentPO.getTalentId();
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        if (talentCertificationInfoPO.getEducation() != null) {
            if (talentCertificationInfoPO.getEducation().contains(educationDTO.getEducation().toString())) {
                return new ResultVO(2558);
            }
        }
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, educationDTO.getEducation().longValue(), (byte) 1);
        if (ifExistInsertCertification != 0) {
            return new ResultVO(2558);
        }
        /**
         * 判断新增还是编辑
         */
        Long insertCertId = educationDTO.getInsertCertId();
        if (insertCertId != null) {
            /**
             * 编辑
             */
            //学历
            InsertEducationPO oldInsertEducationPO =
                    insertEducationMapper.selectByInsertCertId(insertCertId);
            if (oldInsertEducationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertEducationPO.getStatus() != InsertCertificationConstant.rejectStatus) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertEducationPO.setStatus((byte) 10);
            insertEducationMapper.updateByPrimaryKeySelective(oldInsertEducationPO);
            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(insertCertId);
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        } else {
            /**
             * 新增：判断次数是否到3
             */
            ActivcateBO activcateBO = talentMapper.activate(talentPO.getOpenId(), (byte) 1, (byte) 2);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
            Long certId = activcateBO.getCertId();
            Integer educationInsertCertTimes = insertCertificationMapper.
                    findCurrentCertificationTimes(talentPO.getOpenId(), (byte) 1);
            Integer educationCertTimes = educationMapper.findAllByCertId(certId).size();
            if ((educationCertTimes + educationInsertCertTimes) >= 3) {
                return new ResultVO(2670, "该用户待审批和已认证次数已到3！");
            }
        }

        /**
         * 新增
         */
        InsertEducationPO insertEducationPO = new InsertEducationPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertCertificationPO.setType((byte) 1);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(educationDTO.getEducation().longValue());
        insertCertificationPO.setDr((byte) 1);
        insertCertificationMapper.add(insertCertificationPO);
        //学历
        insertEducationPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertEducationPO.setEducation(educationDTO.getEducation());
        insertEducationPO.setEducPicture(educationDTO.getEducPicture());
        insertEducationPO.setEducPicture2(educationDTO.getEducPicture2());
        insertEducationPO.setEducPicture3(educationDTO.getEducPicture3());
        insertEducationPO.setFirstClass(educationDTO.getFirstClass());
        insertEducationPO.setGraduateTime(educationDTO.getGraduateTime());
        insertEducationPO.setMajor(educationDTO.getMajor());
        insertEducationPO.setSchool(educationDTO.getSchool());
        insertEducationPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertEducationPO.setOpenId(educationDTO.getOpenId());
        insertEducationPO.setDr((byte) 1);
        insertEducationPO.setFullTime(educationDTO.getFullTime());
        insertEducationMapper.insertSelective(insertEducationPO);
        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
        insertCertApprovalPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertCertApprovalMapper.insertSelective(insertCertApprovalPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addProfQuality(ProfQualityDTO profQualityDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(profQualityDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         *判断该认证是否重复
         */
        Long talentId = talentPO.getTalentId();
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        if (talentCertificationInfoPO.getPqCategory() != null) {
            if (talentCertificationInfoPO.getPqCategory().contains(profQualityDTO.getCategory().toString())) {
                return new ResultVO(2558);
            }
        }
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profQualityDTO.getCategory().longValue(), (byte) 3);
        if (ifExistInsertCertification != 0) {
            return new ResultVO(2558);
        }

        /**
         * 判断新增还是编辑
         */
        Long insertCertId = profQualityDTO.getInsertCertId();
        if (insertCertId != null) {
            /**
             * 编辑
             */
            //职业资格
            InsertQualityPO oldInsertQualityPO = insertQualityMapper.selectByInsertCertId(insertCertId);
            if (oldInsertQualityPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertQualityPO.getStatus() != InsertCertificationConstant.rejectStatus) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertQualityPO.setStatus((byte) 10);
            insertQualityMapper.updateByPrimaryKeySelective(oldInsertQualityPO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(insertCertId);
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
            /**
             * 清除redis缓存
             */
            iTalentService.clearRedisCache(talentPO.getOpenId());
        } else {
            /**
             * 新增：判断次数是否到3
             */
            ActivcateBO activcateBO = talentMapper.activate(talentPO.getOpenId(), (byte) 1, (byte) 2);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
            Long certId = activcateBO.getCertId();
            Integer qualityInsertCertTimes = insertCertificationMapper.
                    findCurrentCertificationTimes(talentPO.getOpenId(), (byte) 3);
            Integer qualityCertTimes = profQualityMapper.findAllByCertId(certId).size();
            if ((qualityCertTimes + qualityInsertCertTimes) >= 3) {
                return new ResultVO(2670, "该用户待审批和已认证次数已到3！");
            }
        }
        /**
         * 新增
         */
        InsertQualityPO insertQualityPO = new InsertQualityPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertCertificationPO.setType((byte) 3);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(profQualityDTO.getCategory().longValue());
        insertCertificationPO.setDr((byte) 1);
        insertCertificationMapper.add(insertCertificationPO);
        //职业资格
        insertQualityPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertQualityPO.setCategory(profQualityDTO.getCategory());
        insertQualityPO.setInfo(profQualityDTO.getInfo());
        insertQualityPO.setOpenId(profQualityDTO.getOpenId());
        insertQualityPO.setPicture(profQualityDTO.getPicture());
        insertQualityPO.setPicture2(profQualityDTO.getPicture2());
        insertQualityPO.setPicture3(profQualityDTO.getPicture3());
        insertQualityPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertQualityPO.setDr((byte) 1);
        insertQualityMapper.insertSelective(insertQualityPO);
        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
        insertCertApprovalPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertCertApprovalMapper.insertSelective(insertCertApprovalPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addProfTitle(ProfTitleDTO profTitleDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(profTitleDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         *判断该认证是否重复
         */
        Long talentId = talentPO.getTalentId();
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        if (talentCertificationInfoPO.getPtCategory() != null) {
            if (talentCertificationInfoPO.getPtCategory().contains(profTitleDTO.getCategory().toString())) {
                return new ResultVO(2558);
            }
        }
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, profTitleDTO.getCategory().longValue(), (byte) 2);
        if (ifExistInsertCertification != 0) {
            return new ResultVO(2558);
        }

        /**
         * 判断新增还是编辑
         */
        Long insertCertId = profTitleDTO.getInsertCertId();
        if (insertCertId != null) {
            /**
             * 编辑
             */
            InsertTitlePO oldInsertTitlePO = insertTitleMapper.selectByInsertCertId(insertCertId);
            if (oldInsertTitlePO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertTitlePO.getStatus() != InsertCertificationConstant.rejectStatus) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertTitlePO.setStatus((byte) 10);
            insertTitleMapper.updateByPrimaryKeySelective(oldInsertTitlePO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(insertCertId);
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        } else {
            /**
             * 新增：判断次数是否到3
             */
            ActivcateBO activcateBO = talentMapper.activate(talentPO.getOpenId(), (byte) 1, (byte) 2);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
            Long certId = activcateBO.getCertId();
            Integer titleInsertCertTimes = insertCertificationMapper.
                    findCurrentCertificationTimes(talentPO.getOpenId(), (byte) 2);
            Integer titleCertTimes = profTitleMapper.findAllByCertId(certId).size();
            if ((titleCertTimes + titleInsertCertTimes) >= 3) {
                return new ResultVO(2670, "该用户待审批和已认证次数已到3！");
            }
        }
        /**
         * 新增
         */
        InsertTitlePO insertTitlePO = new InsertTitlePO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertCertificationPO.setType((byte) 2);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(profTitleDTO.getCategory().longValue());
        insertCertificationPO.setDr((byte) 1);
        insertCertificationMapper.add(insertCertificationPO);

        insertTitlePO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertTitlePO.setCategory(profTitleDTO.getCategory());
        insertTitlePO.setInfo(profTitleDTO.getInfo());
        insertTitlePO.setOpenId(profTitleDTO.getOpenId());
        insertTitlePO.setPicture(profTitleDTO.getPicture());
        insertTitlePO.setPicture2(profTitleDTO.getPicture2());
        insertTitlePO.setPicture3(profTitleDTO.getPicture3());
        insertTitlePO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertTitlePO.setDr((byte) 1);
        insertTitleMapper.insertSelective(insertTitlePO);

        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
        insertCertApprovalPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertCertApprovalMapper.insertSelective(insertCertApprovalPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(talentHonourDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         *判断该认证是否重复
         */
        Long talentId = talentPO.getTalentId();
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        if (talentCertificationInfoPO.getHonourId() != null) {
            if (talentCertificationInfoPO.getHonourId().contains(talentHonourDTO.getHonourId().toString())) {
                return new ResultVO(2558);
            }
        }
        Integer ifExistInsertCertification = insertCertificationMapper.
                checkIfExistInsertCertification(talentId, talentHonourDTO.getHonourId().longValue(), (byte) 4);
        if (ifExistInsertCertification != 0) {
            return new ResultVO(2558);
        }

        /**
         * 判断新增还是编辑
         */
        Long insertCertId = talentHonourDTO.getInsertCertId();
        if (insertCertId != null) {
            /**
             * 编辑
             */
            InsertHonourPO oldInsertHonourPO = insertHonourMapper.selectByInsertCertId(insertCertId);
            if (oldInsertHonourPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertHonourPO.getStatus() != InsertCertificationConstant.rejectStatus) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertHonourPO.setStatus((byte) 10);
            insertHonourMapper.updateByPrimaryKeySelective(oldInsertHonourPO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(insertCertId);
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        } else {
            /**
             * 新增：判断次数是否到3
             */
            ActivcateBO activcateBO = talentMapper.activate(talentPO.getOpenId(), (byte) 1, (byte) 2);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
            Long certId = activcateBO.getCertId();
            Integer honourInsertCertTimes = insertCertificationMapper.
                    findCurrentCertificationTimes(talentPO.getOpenId(), (byte) 4);
            Integer honourCertTimes = talentHonourMapper.findAllByCertId(certId).size();
            if ((honourCertTimes + honourInsertCertTimes) >= 3) {
                return new ResultVO(2670, "该用户待审批和已认证次数已到3！");
            }
        }

        /**
         * 新增
         */
        InsertHonourPO insertHonourPO = new InsertHonourPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertCertificationPO.setType((byte) 4);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(talentHonourDTO.getHonourId());
        insertCertificationPO.setDr((byte) 1);
        insertCertificationMapper.add(insertCertificationPO);

        insertHonourPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertHonourPO.setHonourId(talentHonourDTO.getHonourId());
        insertHonourPO.setInfo(talentHonourDTO.getInfo());
        insertHonourPO.setOpenId(talentHonourDTO.getOpenId());
        insertHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        insertHonourPO.setHonourPicture2(talentHonourDTO.getHonourPicture2());
        insertHonourPO.setHonourPicture3(talentHonourDTO.getHonourPicture3());
        insertHonourPO.setStatus(InsertCertificationConstant.waitingApproveStatus);
        insertHonourPO.setDr((byte) 1);
        insertHonourMapper.insertSelective(insertHonourPO);

        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
        insertCertApprovalPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertCertApprovalMapper.insertSelective(insertCertApprovalPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO delete(Long insertCertId) {
        InsertCertificationPO insertCertificationPO = insertCertificationMapper.selectByPrimaryKey(insertCertId);
        if (insertCertificationPO == null) {
            return new ResultVO(2551, "查无此新增认证！");
        }

        if (insertCertificationPO.getStatus() != InsertCertificationConstant.rejectStatus) {
            return new ResultVO(2554, "状态不对，此认证无法删除！");
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
            insertEducationPO.setDr((byte) 2);
            insertEducationMapper.updateByPrimaryKeySelective(insertEducationPO);
        } else if (type == 2) {
            //职称
            InsertTitlePO insertTitlePO = insertTitleMapper.selectByInsertCertId(insertCertId);
            if (insertTitlePO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertTitlePO.setDr((byte) 2);
            insertTitleMapper.updateByPrimaryKeySelective(insertTitlePO);
        } else if (type == 3) {
            //职业资格
            InsertQualityPO insertQualityPO = insertQualityMapper.selectByInsertCertId(insertCertId);
            if (insertQualityPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertQualityPO.setDr((byte) 2);
            insertQualityMapper.updateByPrimaryKeySelective(insertQualityPO);

        } else {
            //人才荣誉
            InsertHonourPO insertHonourPO = insertHonourMapper.selectByInsertCertId(insertCertId);
            if (insertHonourPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            insertHonourPO.setDr((byte) 2);
            insertHonourMapper.updateByPrimaryKeySelective(insertHonourPO);

        }
        /**
         * 清除redis缓存
         */
        TalentPO talentPO = talentMapper.selectByPrimaryKey(insertCertificationPO.getTalentId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO) {
        TalentPO talentPO = talentMapper.selectByOpenId(basicInfoDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
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
    public ResultVO findOneDetail(String openId, Long insertCertId) {
        InsertCertificationBO insertCertificationBO = insertCertificationMapper.findOne(openId, insertCertId);
        if (insertCertificationBO == null) {
            return new ResultVO(2551);
        }
        InsertCertificationVO insertCertificationVO = InsertCertificationVO.convert(insertCertificationBO);
        Byte type = insertCertificationVO.getType();
        if (type == null) {
            return new ResultVO(2553, "新增认证type错误为null!");
        }
//        Integer currentCertificationTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, type);
//        insertCertificationVO.setCurrentCertificationTimes(currentCertificationTimes);
        return new ResultVO(1000, insertCertificationVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editPhone(String openId, String phone) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        talentPO.setPhone(phone);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findInsertCertificationTimes(String openId) {
        Integer educTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 1);
        Integer titleTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 2);
        Integer qualityTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 3);
        Integer honourTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 4);
        HashMap<String, Object> hashMap = new HashMap<>(4);
        hashMap.put("educTimes", educTimes);
        hashMap.put("titleTimes", titleTimes);
        hashMap.put("qualityTimes", qualityTimes);
        hashMap.put("honourTimes", honourTimes);
        return new ResultVO(1000, hashMap);
    }

    @Override
    public ResultVO findResultByInsertCertId(Long insertCertId) {
        InsertCertApprovalPO insertCertApprovalPO =
                insertCertApprovalMapper.findResultByInsertCertId(insertCertId, (byte) 2);
        return new ResultVO(1000, insertCertApprovalPO);
    }
}
