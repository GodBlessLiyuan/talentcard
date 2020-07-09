package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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
    private EducationMapper educationMapper;
    @Autowired
    private ProfTitleMapper profTitleMapper;
    @Autowired
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Autowired
    private ITalentService iTalentService;
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    IVerifyTalentPropertyService iVerifyTalentPropertyService;
    @Autowired
    IEditTalentRecordService iEditTalentRecordService;


    @Override
    public ResultVO addEducation(HttpSession httpSession, EducationDTO educationDTO) {
        String openId = educationDTO.getOpenId();
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 学历资格校验是否满足小于等于3，且不重复
         */
        Integer verifyResult = iVerifyTalentPropertyService.verifyEducation(activcateBO, educationDTO);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }

        /**
         *
         * 链接学历职称职业资格人才荣誉
         */
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
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType, EditTalentRecordConstant.educationContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);

    }

    @Override
    public ResultVO addProfQuality(HttpSession httpSession, ProfQualityDTO profQualityDTO) {
        String openId = profQualityDTO.getOpenId();
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */
        Integer verifyResult = iVerifyTalentPropertyService.verifyQuality(activcateBO, profQualityDTO);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
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
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType, EditTalentRecordConstant.qualityContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addProfTitle(HttpSession httpSession, ProfTitleDTO profTitleDTO) {
        String openId = profTitleDTO.getOpenId();
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */
        Integer verifyResult = iVerifyTalentPropertyService.verifyTitle(activcateBO, profTitleDTO);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
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
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType, EditTalentRecordConstant.titleContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO addTalentHonour(HttpSession httpSession, TalentHonourDTO talentHonourDTO) {
        String openId = talentHonourDTO.getOpenId();
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Long certId = activcateBO.getCertId();
        Long talentId = activcateBO.getTalentId();
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */

        Integer verifyResult = iVerifyTalentPropertyService.verifyHonour(activcateBO, talentHonourDTO);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
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
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType, EditTalentRecordConstant.honourContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }


    @Override
    public ResultVO deleteEducation(HttpSession httpSession, String openId, Long educId) {
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
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType, EditTalentRecordConstant.educationContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfQuality(HttpSession httpSession, String openId, Long pqId) {
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
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentPO.getTalentId(), EditTalentRecordConstant.deleteType, EditTalentRecordConstant.qualityContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfTitle(HttpSession httpSession, String openId, Long ptId) {
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
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType, EditTalentRecordConstant.titleContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteTalentHonour(HttpSession httpSession, String openId, Long thId) {
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
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType, EditTalentRecordConstant.honourContent);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        return new ResultVO(1000);
    }
}
