package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.EditTalentPolicyDTO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.service.*;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import com.talentcard.web.vo.CertificationTimesVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    PolicyMapper policyMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    IVerifyTalentPropertyService iVerifyTalentPropertyService;
    @Autowired
    IEditTalentRecordService iEditTalentRecordService;
    @Autowired
    ICertApprovalService iCertApprovalService;
    private byte EDIT_VERIFY = 2;
    @Autowired
    private ILogService logService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editBasicInfo(HttpSession httpSession, BasicInfoDTO basicInfoDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        TalentPO talentPO = talentMapper.selectByOpenId(basicInfoDTO.getOpenId());
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        String beforeJSON = JSONObject.toJSONString(talentPO);
        Long talentId = talentPO.getTalentId();
        talentPO.setPhone(basicInfoDTO.getPhone());
        talentPO.setPolitical(basicInfoDTO.getPolitical());
        talentPO.setIndustry(basicInfoDTO.getIndustry());
        talentPO.setWorkUnit(basicInfoDTO.getWorkUnit());
        talentPO.setWorkLocation(basicInfoDTO.getWorkLocation());
        talentPO.setWorkLocationType(basicInfoDTO.getWorkLocationType());
        talentMapper.updateByPrimaryKeySelective(talentPO);
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType, EditTalentRecordConstant.basicInfoContent,
                beforeJSON, JSONObject.toJSONString(talentPO), basicInfoDTO.getOpinion());
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        messageDTO.setFirst("您好，您的信息已经更新");
        //信息类型
        messageDTO.setKeyword1("基础信息");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + basicInfoDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的基本信息",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editEducation(HttpSession httpSession, EducationDTO educationDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String openId = educationDTO.getOpenId();
        EducationPO educationPO = educationMapper.selectByPrimaryKey(educationDTO.getEducId());
        if (educationPO == null || educationPO.getEducation() == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!talentPO.getTalentId().equals(educationPO.getTalentId())) {
            return new ResultVO(2500);//不匹配查无此人
        }
        /**
         * 学历资格校验是否满足小于等于3，且不重复
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        String beforeJSON = JSONObject.toJSONString(educationPO);
        Integer verifyResult;
        if (educationPO.getEducation().equals(educationDTO.getEducation())) {
            //相等意味着 编辑校验
            verifyResult = iVerifyTalentPropertyService.editVerifyEducation(activcateBO, educationDTO);
        } else {
            //不相等等同于 新增校验
            verifyResult = iVerifyTalentPropertyService.verifyEducation(activcateBO, educationDTO, EDIT_VERIFY);
        }
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 链接学历职称职业资格人才荣誉
         */
        educationPO.setGraduateTime(educationDTO.getGraduateTime());
        educationPO.setFirstClass(educationDTO.getFirstClass());
        educationPO.setEducPicture(educationDTO.getEducPicture());
        educationPO.setEducPicture2(educationDTO.getEducPicture2());
        educationPO.setEducPicture3(educationDTO.getEducPicture3());
        educationPO.setSchool(educationDTO.getSchool());
        educationPO.setMajor(educationDTO.getMajor());
        educationPO.setEducation(educationDTO.getEducation());
        educationPO.setFullTime(educationDTO.getFullTime());
        educationMapper.updateByPrimaryKeySelective(educationPO);
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType,
                EditTalentRecordConstant.educationContent, beforeJSON,
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
        String first = "您好，您的信息已更新。";
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
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的学历信息",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfQuality(HttpSession httpSession, ProfQualityDTO profQualityDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String openId = profQualityDTO.getOpenId();
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        ProfQualityPO profQualityPO = profQualityMapper.selectByPrimaryKey(profQualityDTO.getPqId());
        if (profQualityPO == null || profQualityPO.getCategory() == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!talentPO.getTalentId().equals(profQualityPO.getTalentId())) {
            return new ResultVO(2500);//检测是否匹配
        }
        String beforeJSON = JSONObject.toJSONString(profQualityPO);
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Integer verifyResult;
        if (profQualityPO.getCategory().equals(profQualityDTO.getCategory())) {
            //相等意味着 编辑校验
            verifyResult = iVerifyTalentPropertyService.editVerifyQuality(activcateBO, profQualityDTO);
        } else {
            //不相等等同于 职业资格新增校验
            verifyResult = iVerifyTalentPropertyService.verifyQuality(activcateBO, profQualityDTO, EDIT_VERIFY);
        }
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 编辑
         */
        profQualityPO.setPicture(profQualityDTO.getPicture());
        profQualityPO.setPicture2(profQualityDTO.getPicture2());
        profQualityPO.setPicture3(profQualityDTO.getPicture3());
        profQualityPO.setInfo(profQualityDTO.getInfo());
        profQualityPO.setCategory(profQualityDTO.getCategory());
        profQualityMapper.updateByPrimaryKeySelective(profQualityPO);
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType,
                EditTalentRecordConstant.qualityContent, beforeJSON,
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
        String first = "您好，您的信息已更新。";
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
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的职业资格信息",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editProfTitle(HttpSession httpSession, ProfTitleDTO profTitleDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String openId = profTitleDTO.getOpenId();
        ProfTitlePO profTitlePO = profTitleMapper.selectByPrimaryKey(profTitleDTO.getPtId());
        if (profTitlePO == null || profTitlePO.getCategory() == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!talentPO.getTalentId().equals(profTitlePO.getTalentId())) {
            return new ResultVO(2500);
        }
        String beforeJSON = JSONObject.toJSONString(profTitlePO);
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Integer verifyResult;
        if (profTitlePO.getCategory().equals(profTitleDTO.getCategory())) {
            //相等意味着 编辑校验
            verifyResult = iVerifyTalentPropertyService.editVerifyTitle(activcateBO, profTitleDTO);
        } else {
            //不相等等同于 职业资格新增校验
            verifyResult = iVerifyTalentPropertyService.verifyTitle(activcateBO, profTitleDTO, EDIT_VERIFY);
        }
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }

        /**
         * 编辑
         */
        profTitlePO.setPicture(profTitleDTO.getPicture());
        profTitlePO.setPicture2(profTitleDTO.getPicture2());
        profTitlePO.setPicture3(profTitleDTO.getPicture3());
        profTitlePO.setInfo(profTitleDTO.getInfo());
        profTitlePO.setCategory(profTitleDTO.getCategory());
        profTitleMapper.updateByPrimaryKeySelective(profTitlePO);
        /**
         * 同步更新tci表
         */

        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType,
                EditTalentRecordConstant.titleContent,
                beforeJSON, JSONObject.toJSONString(profTitlePO), profTitleDTO.getOpinion());
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
        String first = "您好，您的信息已更新。";
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
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的职称信息",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentHonour(HttpSession httpSession, TalentHonourDTO talentHonourDTO) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String openId = talentHonourDTO.getOpenId();
        TalentHonourPO talentHonourPO = talentHonourMapper.selectByPrimaryKey(talentHonourDTO.getThId());
        if (talentHonourPO == null || talentHonourPO.getHonourId() == null) {
            return new ResultVO(2661, "查无此认证！");
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        if (!talentPO.getTalentId().equals(talentHonourPO.getTalentId())) {
            return new ResultVO(2500);
        }
        String beforeJSON = JSONObject.toJSONString(talentHonourPO);
        /**
         * 判断次数是否到3
         * 判断该认证是否重复
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            activcateBO = talentMapper.activate(openId, (byte) 4, (byte) 1);
            if (activcateBO == null) {
                return new ResultVO(2900, "新增审批时，人才状态不对！");
            }
        }
        Integer verifyResult;
        if (talentHonourPO.getHonourId().equals(talentHonourDTO.getHonourId())) {
            //相等意味着 编辑校验
            verifyResult = iVerifyTalentPropertyService.editVerifyHonour(activcateBO, talentHonourDTO);
        } else {
            //不相等等同于 职业资格新增校验
            verifyResult = iVerifyTalentPropertyService.verifyHonour(activcateBO, talentHonourDTO, EDIT_VERIFY);
        }
        if (verifyResult != 0) {
            return new ResultVO(verifyResult);
        }
        /**
         * 编辑
         */
        talentHonourPO.setInfo(talentHonourDTO.getInfo());
        talentHonourPO.setHonourPicture(talentHonourDTO.getHonourPicture());
        talentHonourPO.setHonourPicture2(talentHonourDTO.getHonourPicture2());
        talentHonourPO.setHonourPicture3(talentHonourDTO.getHonourPicture3());
        talentHonourPO.setHonourId(talentHonourDTO.getHonourId());
        talentHonourMapper.updateByPrimaryKeySelective(talentHonourPO);
        /**
         * 同步更新tci表
         */
        Long talentId = talentPO.getTalentId();
        Integer updateTciResult = iTalentInfoCertificationService.update(talentId);
        if (updateTciResult != 0) {
            return new ResultVO(2663, "更新tci表失败！");
        }
        //新增EditTalentRecord表 编辑人才记录数据
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType,
                EditTalentRecordConstant.honourContent, beforeJSON,
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
        String first = "您好，您的信息已更新。";
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("主要人才荣誉");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = "变更原因：" + talentHonourDTO.getOpinion();
        messageDTO.setRemark(remark);
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的主要人才荣誉",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO editTalentCategory(HttpSession httpSession, String openId,
                                       String talentCategory, String opinion) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        String beforeJSON = JSONObject.toJSONString(talentPO);
        Long talentId = talentPO.getTalentId();
        talentPO.setCategory(talentCategory);
        //更新uci表
        talentMapper.updateByPrimaryKeySelective(talentPO);
        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentId);//用户的当前信息
        if (userCurrentInfoPO == null) {
            return new ResultVO(2500);
        }
        userCurrentInfoPO.setTalentCategory(talentCategory);
        userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
        /**
         * 更新tci表
         */
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentId);//用户认证完毕后的信息
        if (talentCertificationInfoPO == null) {
            return new ResultVO(2500);
        }
        talentCertificationInfoPO.setTalentCategory(talentCategory);
        talentCertificationInfoMapper.updateByPrimaryKeySelective(talentCertificationInfoPO);
        iEditTalentRecordService.addRecord(httpSession, talentId, EditTalentRecordConstant.editType,
                EditTalentRecordConstant.talentCategoryContent, beforeJSON,
                JSONObject.toJSONString(talentPO), opinion);
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(openId);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的人才类别",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findPolicy(EditTalentPolicyDTO editTalentPolicyDTO) {
        return new ResultVO(1000, policyUtil(editTalentPolicyDTO));
    }

    @Override
    public ResultVO findTalentCertificationDetail(String openId) {
        HashMap<String, Object> hashMap = new HashMap(3);
        hashMap.put("openId", openId);
        hashMap.put("status", (byte) 1);
        //人才信息
        TalentBO talentBO = talentMapper.findOne(hashMap);
        if (talentBO == null) {
            //如果找不到人，怀疑把卡删了，则去搜索待领卡数据。
            hashMap.put("status", (byte) 4);
            talentBO = talentMapper.findOne(hashMap);
            if (talentBO == null) {
                return new ResultVO(2500);
            }
        }
        //卡片信息
        CardPO cardPO = cardMapper.selectByPrimaryKey(talentBO.getCardId());
        HashMap<String, Object> talentCard = userCardMapper.findCurrentCard(openId, (byte) 2);
        if (talentCard == null) {
            //找不到正常使用的卡就去找待领取的卡
            talentCard = userCardMapper.findCurrentCard(openId, (byte) 1);
            if (talentCard == null) {
                return new ResultVO(2500);
            }
        }
        talentBO.setCardNum((String) talentCard.get("code"));
        /**
         * 政策查询
         */
        Long talentId = talentBO.getTalentId();
        List<Integer> educationList = educationMapper.queryNameByTalentId(talentId);
        List<Integer> titleList = profTitleMapper.queryNameByTalentId(talentId);
        List<Integer> qualityList = profQualityMapper.queryNameByTalentId(talentId);
        List<Long> honourList = talentHonourMapper.queryNameByTalentId(talentId);
        EditTalentPolicyDTO editTalentPolicyDTO = new EditTalentPolicyDTO();
        editTalentPolicyDTO.setCardId(talentBO.getCardId());
        editTalentPolicyDTO.setCategory(talentBO.getCategory());
        editTalentPolicyDTO.setEducationList(educationList);
        editTalentPolicyDTO.setTitleList(titleList);
        editTalentPolicyDTO.setQualityList(qualityList);
        editTalentPolicyDTO.setHonourList(honourList);
        List<PolicyPO> policyPOList = policyUtil(editTalentPolicyDTO);
        /**
         * 已认证次数+待审批次数
         */
        Integer educationTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 1);
        Integer titleTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 2);
        Integer qualityTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 3);
        Integer honourTimes = insertCertificationMapper.findCurrentCertificationTimes(openId, (byte) 4);
        CertificationTimesVO certificationTimesVO = new CertificationTimesVO();
        certificationTimesVO.setEducationTimes(educationTimes + talentBO.getEducationPOList().size());
        certificationTimesVO.setTitleTimes(titleTimes + talentBO.getProfTitlePOList().size());
        certificationTimesVO.setQualityTimes(qualityTimes + talentBO.getProfQualityPOList().size());
        certificationTimesVO.setHonourTimes(honourTimes + talentBO.getTalentHonourPOList().size());
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("talentInfo", talentBO);
        result.put("policyPOList", policyPOList);
        result.put("cardInfo", cardPO);
        result.put("certificationTimes", certificationTimesVO);
        return new ResultVO(1000, result);
    }

    /**
     * 根据talentId和cardId，高级卡更换高级卡
     *
     * @param talentId
     * @param newCardId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO changeCard(HttpSession session,Long talentId, Long newCardId, String opinion) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        String openId = talentPO.getOpenId();

        //找到uc表status=2，当前正在使用的卡的cardId
        ActivcateBO talentInfo = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (talentInfo == null) {
            return new ResultVO(2500);
        }
        Long oldCardId = talentInfo.getCardId();
        if (newCardId.equals(oldCardId)) {
            return new ResultVO(2671, "更新卡与原本拥有卡号重复！");
        }


        CardPO oldCardPO = cardMapper.selectByPrimaryKey(oldCardId);
        CardPO newCardPO = cardMapper.selectByPrimaryKey(newCardId);
        UserCardPO oldUserCardPO = userCardMapper.selectByPrimaryKey(talentInfo.getUcId());
        if (oldCardPO == null || newCardPO == null || oldUserCardPO == null) {
            return new ResultVO<>(2600, "查无此卡！");
        }
        //当前编号
        String currentNum = oldUserCardPO.getCurrentNum();
        //人才卡编号
        String membershipNumber = newCardPO.getInitialWord() + oldCardPO.getAreaNum() + currentNum;
        //新增uc表状态1；更新card表
        UserCardPO newUserCardPO = new UserCardPO();
        newUserCardPO.setCardId(newCardId);
        newUserCardPO.setTalentId(talentId);
        newUserCardPO.setNum(membershipNumber);
        newUserCardPO.setName(newCardPO.getTitle());
        newUserCardPO.setCurrentNum(currentNum);
        newUserCardPO.setCreateTime(new Date());
        newUserCardPO.setStatus((byte) 1);
        userCardMapper.insertSelective(newUserCardPO);

        //更新老uc表
        oldUserCardPO.setStatus((byte) 3);
        userCardMapper.updateByPrimaryKeySelective(oldUserCardPO);

        //更新认证表状态
        certificationMapper.updateStatusByCertId(talentInfo.getCertId(), (byte) 4);

        /**
         * 更新card表新旧卡
         */
        //新卡待领取数量+1
        newCardPO.setWaitingMemberNum(newCardPO.getWaitingMemberNum() + 1);
        cardMapper.updateByPrimaryKeySelective(newCardPO);
        //旧卡会员数量-1
        oldCardPO.setWaitingMemberNum(oldCardPO.getMemberNum() - 1);
        cardMapper.updateByPrimaryKeySelective(oldCardPO);

        /**
         * 更新talent表
         */
        talentPO.setCardId(newCardId);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        /**
         * 设置旧卡券失效
         */
        if (oldCardId != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", talentInfo.getCode());
            jsonObject.put("card_id", talentInfo.getWxCardId());
            String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                    + AccessTokenUtil.getAccessToken();
            WechatApiUtil.postRequest(url, jsonObject);
        }

        /**
         * 用模版推送消息
         */
        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(talentPO.getOpenId());
        //开头
        String first = "您好，您的信息已更新。原人才卡作废，请及时领取新的人才卡" +
                newCardPO.getTitle() + newCardPO.getInitialWord();
        messageDTO.setFirst(first);
        //信息类型
        messageDTO.setKeyword1("人才卡");
        //变更时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword2(currentTime);
        //模版编号
        messageDTO.setTemplateId(4);
        //结束
        String remark = opinion;
        messageDTO.setRemark(remark);
        messageDTO.setUrl(WebParameterUtil.getIndexUrl());
        MessageUtil.sendTemplateMessage(messageDTO);
        /**
         * 清缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentManager,OpsRecordMenuConstant.S_ConfirmTalent,
                "编辑人才\"%s\"的人才卡",talentPO.getName());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findEnableChangeCard(Long talentId) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        //使用过的卡
        List<CardPO> usedCards = cardMapper.findUsedCard(talentId);
        //现在有高级卡
        List<CardPO> seniorCards = cardMapper.findSeniorCard(null);
        if (usedCards == null || seniorCards == null || usedCards.size() == 0 || seniorCards.size() == 0) {
            return new ResultVO(2600);
        }
        List<CardPO> resultCards = new ArrayList<>();
        Integer flag = 0;
        for (CardPO seniorCard : seniorCards) {
            for (CardPO usedCard : usedCards) {
                if (usedCard.getCardId().equals(seniorCard.getCardId())) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                resultCards.add(seniorCard);
            }
            //加上当前使用的卡
            if (seniorCard.getCardId().equals(talentPO.getCardId())) {
                resultCards.add(seniorCard);
            }
            flag = 0;
        }
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        return new ResultVO(1000, resultCards);
    }

    /**
     * 政策查询工具类
     *
     * @param editTalentPolicyDTO
     * @return
     */
    private List<PolicyPO> policyUtil(EditTalentPolicyDTO editTalentPolicyDTO) {
        List<Integer> existEducations = editTalentPolicyDTO.getEducationList();
        List<Integer> existTitles = editTalentPolicyDTO.getTitleList();
        List<Integer> existQualities = editTalentPolicyDTO.getQualityList();
        List<Long> existHonours = editTalentPolicyDTO.getHonourList();
        String[] existCategories = null;
        if (null != editTalentPolicyDTO.getCategory()) {
            existCategories = editTalentPolicyDTO.getCategory().split(",");
        }

        Long existCardId = editTalentPolicyDTO.getCardId();

        List<PolicyPO> pos = policyMapper.queryByDr((byte) 1);
        List<PolicyPO> showPOs = new ArrayList<>();
        for (PolicyPO po : pos) {
            String[] cardIds = null;
            String[] categories = null;
            String[] educations = null;
            String[] titles = null;
            String[] qualities = null;
            String[] honourIds = null;
            if (null != po.getCards()) {
                cardIds = po.getCards().split(",");
            }
            if (null != po.getCategories()) {
                categories = po.getCategories().split(",");
            }
            if (null != po.getEducations()) {
                educations = po.getEducations().split(",");
            }
            if (null != po.getTitles()) {
                titles = po.getTitles().split(",");
            }
            if (null != po.getQualities()) {
                qualities = po.getQualities().split(",");
            }
            if (null != po.getHonourIds()) {
                honourIds = po.getHonourIds().split(",");
            }

            boolean show = false;
            if (null != cardIds && null != existCardId) {
                for (String cardId : cardIds) {
                    if (!StringUtils.isEmpty(cardId) && existCardId.toString().equals(cardId)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != categories && null != existCategories) {
                for (String category : categories) {
                    if (!show) {
                        for (String existCategory : existCategories) {
                            if (!StringUtils.isEmpty(existCategory) && category.equals(existCategory)) {
                                show = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!show && null != educations && null != existEducations) {
                for (String educ : educations) {
                    if (!StringUtils.isEmpty(educ) && existEducations.toString().contains(educ)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != titles && null != existTitles) {
                for (String title : titles) {
                    if (!StringUtils.isEmpty(title) && existTitles.toString().contains(title)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != qualities && null != existQualities) {
                for (String quality : qualities) {
                    if (!StringUtils.isEmpty(quality) && existQualities.toString().contains(quality)) {
                        show = true;
                        break;
                    }
                }
            }

            if (!show && null != honourIds && null != existHonours) {
                for (String honourId : honourIds) {
                    if (!StringUtils.isEmpty(honourId) && existHonours.toString().contains(honourId)) {
                        show = true;
                        break;
                    }
                }
            }

            if (show) {
                showPOs.add(po);
            }
        }
        return showPOs;
    }

    /**
     * 证件号码，后四位加密,打星星
     *
     * @return
     */
    public String identificationCardEncryption(TalentPO talentPO) {

        Byte cardType = talentPO.getCardType();
        String identificationCardNum = "";
        if (cardType == 1) {
            //身份证
            identificationCardNum = talentPO.getIdCard();
        } else if (cardType == 2) {
            //护照
            identificationCardNum = talentPO.getPassport();
        } else if (cardType == 3) {
            //驾照
            identificationCardNum = talentPO.getDriverCard();
        }
        if (StringUtils.isEmpty(identificationCardNum) || identificationCardNum.length() <= 4) {
            return "当前号码出现异常！";
        }
        Integer end = identificationCardNum.length() - 4;
        String encryptionIdCardNum = identificationCardNum.substring(0, end) + "****";
        return encryptionIdCardNum;
    }

}
