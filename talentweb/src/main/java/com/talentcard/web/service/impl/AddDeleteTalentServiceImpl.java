package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.EditTalentConstant;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.*;
import com.talentcard.web.utils.MessageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private byte ADD_VERIFY = 1;


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
        Integer verifyResult = iVerifyTalentPropertyService.verifyEducation(activcateBO, educationDTO, ADD_VERIFY);
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
        educationPO.setEducPicture2(educationDTO.getEducPicture2());
        educationPO.setEducPicture3(educationDTO.getEducPicture3());
        educationPO.setSchool(educationDTO.getSchool());
        educationPO.setMajor(educationDTO.getMajor());
        educationPO.setEducation(educationDTO.getEducation());
        educationPO.setIfCertificate((byte) 1);
        educationPO.setCertId(certId);
        educationPO.setStatus((byte) 1);
        educationPO.setTalentId(talentId);
        educationPO.setFullTime(educationDTO.getFullTime());
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
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType,
                EditTalentRecordConstant.educationContent, "",
                JSONObject.toJSONString(educationPO), educationDTO.getOpinion());
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String educ = EditTalentConstant.educationMap.get(educationDTO.getEducation());
        if (StringUtils.isEmpty(educ)) {
            educ = "";
        }
        String first = "您好，您的信息已更新，为您新增学历" + educ;
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("学历");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + educationDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
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
        Integer verifyResult = iVerifyTalentPropertyService.verifyQuality(activcateBO, profQualityDTO, ADD_VERIFY);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setPicture(profQualityDTO.getPicture());
        profQualityPO.setPicture2(profQualityDTO.getPicture2());
        profQualityPO.setPicture3(profQualityDTO.getPicture3());
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
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType,
                EditTalentRecordConstant.qualityContent, "",
                JSONObject.toJSONString(profQualityPO), profQualityDTO.getOpinion());
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String quality = EditTalentConstant.qualityMap.get(profQualityDTO.getCategory());
        if (StringUtils.isEmpty(quality)) {
            quality = "";
        }
        String first = "您好，您的信息已更新，为您新增职业资格" + quality;
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职业资格");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + profQualityDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
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
        Integer verifyResult = iVerifyTalentPropertyService.verifyTitle(activcateBO, profTitleDTO, ADD_VERIFY);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setPicture(profTitleDTO.getPicture());
        profTitlePO.setPicture2(profTitleDTO.getPicture2());
        profTitlePO.setPicture3(profTitleDTO.getPicture3());
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
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType,
                EditTalentRecordConstant.titleContent, "",
                JSONObject.toJSONString(profTitlePO), profTitleDTO.getOpinion());
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String title = EditTalentConstant.qualityMap.get(profTitleDTO.getCategory());
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        String first = "您好，您的信息已更新，为您新增职称" + title;
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职称");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + profTitleDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
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

        Integer verifyResult = iVerifyTalentPropertyService.verifyHonour(activcateBO, talentHonourDTO, ADD_VERIFY);
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setInfo(talentHonourDTO.getInfo());
        talentHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        talentHonourPO.setHonourPicture2(talentHonourDTO.getHonourPicture2());
        talentHonourPO.setHonourPicture3(talentHonourDTO.getHonourPicture3());
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
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.addType,
                EditTalentRecordConstant.honourContent, "",
                JSONObject.toJSONString(talentHonourPO), talentHonourDTO.getOpinion());
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String honour = EditTalentConstant.qualityMap.get(talentHonourDTO.getHonourId());
        if (StringUtils.isEmpty(honour)) {
            honour = "";
        }
        String first = "您好，您的信息已更新，为您新增人才荣誉" + honour;
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职称");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + talentHonourDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }


    @Override
    public ResultVO deleteEducation(HttpSession httpSession, String openId, Long educId, String opinion) {
        //查教育
        EducationPO educationPO = educationMapper.selectByPrimaryKey(educId);
        if (educationPO == null) {
            return new ResultVO(2662, "删除失败！");
        }
        //查人才
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            //查无此人
            return new ResultVO(2500);
        }
        //不匹配则查无此人
        if (!educationPO.getTalentId().equals(talentPO.getTalentId())) {
            return new ResultVO(2500);
        }
        //再次删除
        Integer result = educationMapper.deleteByPrimaryKey(educId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }

        String beforeJson = JSONObject.toJSONString(educationPO);

        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType,
                EditTalentRecordConstant.educationContent, beforeJson, "", opinion);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String educ = EditTalentConstant.educationMap.get(educationPO.getEducation());
        if (StringUtils.isEmpty(educ)) {
            educ = "";
        }
        String first = "您好，您的学历\"" + educ + "\"因不符合条件，已被删除";
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("学历");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfQuality(HttpSession httpSession, String openId, Long pqId, String opinion) {
        //查职业资格表
        ProfQualityPO profQualityPO = profQualityMapper.selectByPrimaryKey(pqId);
        if (profQualityPO == null) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        //查人才
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        //验证匹配
        if (!profQualityPO.getTalentId().equals(talentPO.getTalentId())) {
            //查无此人
            return new ResultVO(2500);
        }
        //删除
        Integer result = profQualityMapper.deleteByPrimaryKey(pqId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentPO.getTalentId(),
                EditTalentRecordConstant.deleteType, EditTalentRecordConstant.qualityContent,
                JSONObject.toJSONString(profQualityPO), "", opinion);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String quality = EditTalentConstant.qualityMap.get(profQualityPO.getCategory());
        if (StringUtils.isEmpty(quality)) {
            quality = "";
        }
        String first = "您好，您的职业资格\"" + quality + "\"因不符合条件，已被删除";
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职业资格");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteProfTitle(HttpSession httpSession, String openId, Long ptId, String opinion) {
        //先查询，再查人才表，两个的id不一致，返回2500
        ProfTitlePO profTitlePO = profTitleMapper.selectByPrimaryKey(ptId);
        if (profTitlePO == null) {
            return new ResultVO(2662, "删除失败！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!profTitlePO.getTalentId().equals(talentPO.getTalentId())) {
            return new ResultVO(2500);
        }
        Integer result = profTitleMapper.deleteByPrimaryKey(ptId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType,
                EditTalentRecordConstant.titleContent, JSONObject.toJSONString(profTitlePO), "", opinion);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String title = EditTalentConstant.qualityMap.get(profTitlePO.getCategory());
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        String first = "您好，您的职称\"" + title + "\"因不符合条件，已被删除";
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职称");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO deleteTalentHonour(HttpSession httpSession, String openId, Long thId, String opinion) {
        TalentHonourPO talentHonourPO = talentHonourMapper.selectByPrimaryKey(thId);
        if (talentHonourPO == null) {
            return new ResultVO(2662, "删除失败！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!talentPO.getTalentId().equals(talentHonourPO.getTalentId())) {
            return new ResultVO(2500);
        }
        Integer result = talentHonourMapper.deleteByPrimaryKey(thId);
        if (result != 1) {
            return new ResultVO(2662, "删除失败！");
        }
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentPO.getTalentId());
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.deleteType,
                EditTalentRecordConstant.honourContent,
                JSONObject.toJSONString(talentHonourPO), "", opinion);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String honour = EditTalentConstant.qualityMap.get(talentHonourPO.getHonourId());
        if (StringUtils.isEmpty(honour)) {
            honour = "";
        }
        String first = "您好，您的主要人才荣誉\"" + honour + "\"因不符合条件，已被删除";
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("职称");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);
        return new ResultVO(1000);
    }
}
