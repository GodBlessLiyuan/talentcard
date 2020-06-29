package com.talentcard.front.service.impl;

import com.talentcard.common.bo.InsertCertificationBO;
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
    ITalentService iTalentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addEducation(EducationDTO educationDTO) {
        Long insertEducId = educationDTO.getInsertEducId();
        TalentPO talentPO = talentMapper.selectByOpenId(educationDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         * 判断新增还是编辑
         */
        if (insertEducId != null) {
            /**
             * 编辑
             */
            //学历
            InsertEducationPO oldInsertEducationPO =
                    insertEducationMapper.selectByPrimaryKey(insertEducId);
            if (oldInsertEducationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertEducationPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertEducationPO.setStatus((byte) 10);
            insertEducationMapper.updateByPrimaryKeySelective(oldInsertEducationPO);
            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(educationDTO.getInsertCertId());
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        }

        /**
         * 新增
         */
        InsertEducationPO insertEducationPO = new InsertEducationPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus((byte) 2);
        insertCertificationPO.setType((byte) 1);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(educationDTO.getEducation().longValue());
        insertCertificationPO.setDr((byte)1);
        insertCertificationMapper.add(insertCertificationPO);
        //学历
        insertEducationPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertEducationPO.setEducation(educationDTO.getEducation());
        insertEducationPO.setEducPicture(educationDTO.getEducPicture());
        insertEducationPO.setFirstClass(educationDTO.getFirstClass());
        insertEducationPO.setGraduateTime(educationDTO.getGraduateTime());
        insertEducationPO.setMajor(educationDTO.getMajor());
        insertEducationPO.setSchool(educationDTO.getSchool());
        insertEducationPO.setStatus((byte) 2);
        insertEducationPO.setOpenId(educationDTO.getOpenId());
        insertEducationPO.setDr((byte) 1);
        insertEducationMapper.insertSelective(insertEducationPO);
        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
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
        Long insertQualityId = profQualityDTO.getInsertQualityId();
        TalentPO talentPO = talentMapper.selectByOpenId(profQualityDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         * 判断新增还是编辑
         */
        if (insertQualityId != null) {
            /**
             * 编辑
             */
            //职业资格
            InsertQualityPO oldInsertQualityPO = insertQualityMapper.selectByPrimaryKey(insertQualityId);
            if (oldInsertQualityPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertQualityPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertQualityPO.setStatus((byte) 10);
            insertQualityMapper.updateByPrimaryKeySelective(oldInsertQualityPO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(profQualityDTO.getInsertCertId());
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
            /**
             * 清除redis缓存
             */
            iTalentService.clearRedisCache(talentPO.getOpenId());
        }
        /**
         * 新增
         */
        InsertQualityPO insertQualityPO = new InsertQualityPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus((byte) 2);
        insertCertificationPO.setType((byte) 3);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(profQualityDTO.getCategory().longValue());
        insertCertificationPO.setDr((byte)1);
        insertCertificationMapper.add(insertCertificationPO);
        //职业资格
        insertQualityPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertQualityPO.setCategory(profQualityDTO.getCategory());
        insertQualityPO.setInfo(profQualityDTO.getInfo());
        insertQualityPO.setOpenId(profQualityDTO.getOpenId());
        insertQualityPO.setPicture(profQualityDTO.getPicture());
        insertQualityPO.setStatus((byte) 2);
        insertQualityPO.setDr((byte) 1);
        insertQualityMapper.insertSelective(insertQualityPO);
        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
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
        Long insertTitleId = profTitleDTO.getInsertTitleId();
        TalentPO talentPO = talentMapper.selectByOpenId(profTitleDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         * 判断新增还是编辑
         */
        if (insertTitleId != null) {
            /**
             * 编辑
             */
            InsertTitlePO oldInsertTitlePO = insertTitleMapper.selectByPrimaryKey(insertTitleId);
            if (oldInsertTitlePO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertTitlePO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertTitlePO.setStatus((byte) 10);
            insertTitleMapper.updateByPrimaryKeySelective(oldInsertTitlePO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(profTitleDTO.getInsertCertId());
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        }
        /**
         * 新增
         */
        InsertTitlePO insertTitlePO = new InsertTitlePO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus((byte) 2);
        insertCertificationPO.setType((byte) 2);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(profTitleDTO.getCategory().longValue());
        insertCertificationPO.setDr((byte)1);
        insertCertificationMapper.add(insertCertificationPO);

        insertTitlePO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertTitlePO.setCategory(profTitleDTO.getCategory());
        insertTitlePO.setInfo(profTitleDTO.getInfo());
        insertTitlePO.setOpenId(profTitleDTO.getOpenId());
        insertTitlePO.setPicture(profTitleDTO.getPicture());
        insertTitlePO.setStatus((byte) 2);
        insertTitlePO.setDr((byte) 1);
        insertTitleMapper.insertSelective(insertTitlePO);

        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
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
        Long insertTalentHonourId = talentHonourDTO.getInsertTalentHonourId();
        TalentPO talentPO = talentMapper.selectByOpenId(talentHonourDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO<>(2500);
        }
        if (talentPO.getStatus() != 1) {
            return new ResultVO<>(2555, "人才无新增认证权限！");
        }
        /**
         * 判断新增还是编辑
         */
        if (insertTalentHonourId != null) {
            /**
             * 编辑
             */
            InsertHonourPO oldInsertHonourPO = insertHonourMapper.selectByPrimaryKey(insertTalentHonourId);
            if (oldInsertHonourPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            if (oldInsertHonourPO.getStatus() != 3) {
                return new ResultVO(2552, "状态不对，此认证无法编辑！");
            }
            oldInsertHonourPO.setStatus((byte) 10);
            insertHonourMapper.updateByPrimaryKeySelective(oldInsertHonourPO);

            //认证
            InsertCertificationPO oldInsertCertificationPO =
                    insertCertificationMapper.selectByPrimaryKey(talentHonourDTO.getInsertCertId());
            if (oldInsertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
            oldInsertCertificationPO.setStatus((byte) 10);
            insertCertificationMapper.updateByPrimaryKeySelective(oldInsertCertificationPO);
        }

        /**
         * 新增
         */
        InsertHonourPO insertHonourPO = new InsertHonourPO();
        //新增认证表
        InsertCertificationPO insertCertificationPO = new InsertCertificationPO();
        insertCertificationPO.setCreateTime(new Date());
        insertCertificationPO.setStatus((byte) 2);
        insertCertificationPO.setType((byte) 4);
        insertCertificationPO.setTalentId(talentPO.getTalentId());
        insertCertificationPO.setCertInfo(talentHonourDTO.getHonourId());
        insertCertificationPO.setDr((byte)1);
        insertCertificationMapper.add(insertCertificationPO);

        insertHonourPO.setInsertCertId(insertCertificationPO.getInsertCertId());
        insertHonourPO.setHonourId(talentHonourDTO.getHonourId());
        insertHonourPO.setInfo(talentHonourDTO.getInfo());
        insertHonourPO.setOpenId(talentHonourDTO.getOpenId());
        insertHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        insertHonourPO.setStatus((byte) 2);
        insertHonourPO.setDr((byte) 1);
        insertHonourMapper.insertSelective(insertHonourPO);

        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setInsertCertId(insertCertApprovalPO.getInsertCertId());
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setResult((byte) 8);
        insertCertApprovalPO.setType((byte) 1);
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
            if (insertCertificationPO == null) {
                return new ResultVO(2551, "查无此新增认证！");
            }
        }
        if (insertCertificationPO.getStatus() != 3) {
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
        if(talentPO==null){
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
}
