package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.dto.IdentificationDTO;
import com.talentcard.front.dto.MessageDTO;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.utils.AccessTokenUtil;
import com.talentcard.front.utils.CardNumberUtil;
import com.talentcard.front.utils.MessageUtil;
import com.talentcard.common.utils.TalentActivityUtil;
import com.talentcard.front.vo.TalentVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人才注册/认证相关
 *
 * @author ChenXU
 */
@EnableTransactionManagement
@Service
public class TalentServiceImpl implements ITalentService {
    private static final Logger logger = LoggerFactory.getLogger(TalentServiceImpl.class);
    @Autowired
    private TalentMapper talentMapper;
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
    private CertApprovalMapper certApprovalMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TestTalentInfoMapper testTalentInfoMapper;
    @Autowired
    private InsertCertificationMapper insertCertificationMapper;
    @Autowired
    private TalentCardHoldListMapper talentCardHoldListMapper;
    @Autowired
    private CertExamineRecordMapper certExamineRecordMapper;
    @Autowired
    TalentCertificationInfoMapper talentCertificationInfoMapper;

    @Override
    public ResultVO<TalentPO> findStatus(String openId) {

        /**
         * 添加redis hash缓存
         */
        String mapStr = this.redisMapUtil.hget(openId, "findStatus");
        Map<String, String> cacheResult = StringToObjUtil.strToObj(mapStr, Map.class);
        if (cacheResult != null) {
            return new ResultVO(1000, cacheResult);
        }

        //待领取的卡
        HashMap<String, Object> getCard = userCardMapper.findCurrentCard(openId, (byte) 1);

        HashMap<String, Object> result = new HashMap(4);

        if (getCard != null) {
            //说明存在待领取的卡，优先给待领取的
            result.put("status", 1);
            result.put("cardId", getCard.get("cardId"));
            result.put("code", getCard.get("code"));
        } else {
            //正在使用的卡
            HashMap<String, Object> currentCard = userCardMapper.findCurrentCard(openId, (byte) 2);

            if (currentCard == null) {
                //都为空，说明这个人没有卡，需要注册
                result.put("status", 2);
                result.put("name", "游客");
                result.put("code", "000000000");

                return new ResultVO(1000, result);
            } else {
                //实在不行，给正在使用的卡
                result.put("status", 1);
                result.put("cardId", currentCard.get("cardId"));
                result.put("code", currentCard.get("code"));
            }
        }

        Integer ifChangeCard = talentMapper.ifExistGetCard(openId);
        if (ifChangeCard == 0) {
            result.put("ifChangeCard", 2);
        } else {
            result.put("ifChangeCard", 1);
        }
        Integer ifInAudit = talentMapper.ifInAudit(openId);
        if (ifInAudit != 0) {
            //找到待审批的
            result.put("ifInAudit", 1);
        } else {
            //找不到待审批的
            result.put("ifInAudit", 2);
        }
        TalentPO talentPO = talentMapper.selectByOpenId(openId);

        if (talentPO != null) {

            if (StringUtils.isEmpty(talentPO.getUnionId())) {
                String unionId = getUnionIdByOpenId(openId);
                if (!StringUtils.isEmpty(unionId)) {
                    talentPO.setUnionId(unionId);
                    talentMapper.updateByPrimaryKey(talentPO);
                }
            }

            if (talentPO.getStatus() == 1) {
                result.put("ifCertificate", 1);
            } else {
                result.put("ifCertificate", 2);
            }
            result.put("name", talentPO.getName());
            /**
             * 设置缓存
             */
            this.redisMapUtil.hset(openId, "findStatus", JSON.toJSONString(result));
        } else {
            result.put("name", "游客");
        }

        return new ResultVO(1000, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO register(JSONObject jsonObject) {
        //判断是否注册过
        String openId = jsonObject.getString("openId");
        TalentPO ifExist = talentMapper.selectByOpenId(openId);
        if (ifExist != null) {
            return new ResultVO(2305, "该openId已被注册");
        }
        //身份证明卡：身份证、护照、驾照
        String idCard = jsonObject.getString("idCard");
        String passport = jsonObject.getString("passport");
        String driverCard = jsonObject.getString("driverCard");
        Byte cardType = jsonObject.getByte("cardType");
        String identificationCardNum = "";
        if (cardType == 1) {
            identificationCardNum = idCard;
            if (idCard != null) {
                if (idCard.length() != 18) {
                    return new ResultVO(2306, "身份证不是18位或者倒数第二位不是数字");
                }
                //判断身份证号倒数第二位是否是数字
                Boolean strResult = Character.isDigit(idCard.charAt(16));
                if (strResult == false) {
                    return new ResultVO(2306, "身份证不是18位或者倒数第二位不是数字");
                }
                //身份证唯一性校验
                Integer idCardExist = talentMapper.idCardIfUnique(idCard);
                if (idCardExist != 0) {
                    return new ResultVO(2306, "该身份证号已被注册");
                }
            }
        } else if (cardType == 2) {
            identificationCardNum = passport;
        } else if (cardType == 3) {
            identificationCardNum = driverCard;
        }
        if (StringUtils.isEmpty(identificationCardNum) || identificationCardNum.length() <= 4) {
            return new ResultVO(2310, "证件号码长度过短，不符合规范");
        }
        //设置状态值 状态3为注册中
        Byte status = (byte) 2;
        //人才表
        TalentPO talentPO = new TalentPO();
        talentPO.setOpenId(openId);
        talentPO.setName(jsonObject.getString("name"));
        //通过身份证号判断性别
        talentPO.setSex(jsonObject.getByte("sex"));
        talentPO.setCardType(cardType);
        talentPO.setIdCard(idCard);
        talentPO.setPassport(passport);
        talentPO.setDriverCard(driverCard);
        talentPO.setWorkUnit(jsonObject.getString("workUnit"));
        talentPO.setWorkLocation(jsonObject.getString("workLocation"));
        talentPO.setWorkLocationType(jsonObject.getByte("workLocationType"));
        talentPO.setIndustry(jsonObject.getInteger("industry"));
        talentPO.setIndustrySecond(jsonObject.getInteger("industrySecond"));
        talentPO.setPhone(jsonObject.getString("phone"));
        talentPO.setCreateTime(new Date());
        talentPO.setStatus(status);
        talentPO.setPolitical(jsonObject.getByte("political"));
        talentPO.setDr((byte) 1);
        talentPO.setTalentSource(jsonObject.getInteger("talentSource"));
        //人才表的初级卡cardId
        CardPO cardPO = cardMapper.findDefaultCard();
        Long cardId = cardPO.getCardId();
        talentPO.setCardId(cardId);

        /**
         * @author xiahui
         * 根据 openid 查询 unionid, 插入到人才表中
         */
        String unionId = getUnionIdByOpenId(openId);
        if (StringUtils.isEmpty(unionId)) {
            if (jsonObject.containsKey("token")) {
                String access_token = jsonObject.getString("token");
                unionId = getUserInfoUnionId(access_token, openId);
            }
        }

        talentPO.setUnionId(unionId);
        talentMapper.add(talentPO);
        Long talentId = talentPO.getTalentId();

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setType((byte) 1);
        certificationPO.setPolitical(jsonObject.getByte("political"));
        certificationMapper.add(certificationPO);
        Long certificationId = certificationPO.getCertId();

        //学历表
        EducationPO educationPO = new EducationPO();
        educationPO.setEducation(jsonObject.getInteger("education"));
        educationPO.setSchool(jsonObject.getString("school"));
        educationPO.setFirstClass(jsonObject.getByte("firstClass"));
        educationPO.setMajor(jsonObject.getString("major"));
        educationPO.setCertId(certificationId);
        educationPO.setTalentId(talentId);
        educationPO.setStatus(status);
        educationPO.setIfCertificate((byte) 10);
        educationPO.setGraduateTime(jsonObject.getString("graduateTime"));
        educationPO.setFullTime(jsonObject.getByte("fullTime"));
        if (educationPO.getEducation() != null) {
            educationMapper.insertSelective(educationPO);
        }

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCategory(jsonObject.getInteger("profTitleCategory"));
        profTitlePO.setInfo(jsonObject.getString("profTitleInfo"));
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        profTitlePO.setIfCertificate((byte) 10);
        if (profTitlePO.getCategory() != null) {
            profTitleMapper.insertSelective(profTitlePO);
        }

        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCategory(jsonObject.getInteger("profQualityCategory"));
        profQualityPO.setInfo(jsonObject.getString("profQualityInfo"));
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        profQualityPO.setIfCertificate((byte) 10);
        if (profQualityPO.getCategory() != null) {
            profQualityMapper.insertSelective(profQualityPO);
        }
        //荣誉表
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setHonourId(jsonObject.getLong("honourId"));
        talentHonourPO.setCertId(certificationId);
        talentHonourPO.setTalentId(talentId);
        talentHonourPO.setStatus(status);
        talentHonourPO.setIfCertificate((byte) 10);
        if (talentHonourPO.getHonourId() != null) {
            talentHonourMapper.insertSelective(talentHonourPO);
        }
        //插入基本信息表
        UserCurrentInfoPO userCurrentInfoPO = new UserCurrentInfoPO();
        userCurrentInfoPO.setTalentId(talentId);
        userCurrentInfoPO.setEducation(jsonObject.getInteger("education"));
        userCurrentInfoPO.setSchool(jsonObject.getString("school"));
        userCurrentInfoPO.setFirstClass(jsonObject.getByte("firstClass"));
        userCurrentInfoPO.setMajor(jsonObject.getString("major"));
        userCurrentInfoPO.setPtCategory(jsonObject.getInteger("profTitleCategory"));
        userCurrentInfoPO.setPtInfo(jsonObject.getString("profTitleInfo"));
        userCurrentInfoPO.setPqCategory(jsonObject.getInteger("profQualityCategory"));
        userCurrentInfoPO.setPqInfo(jsonObject.getString("profQualityInfo"));
        userCurrentInfoPO.setPolitical(jsonObject.getByte("political"));
        userCurrentInfoPO.setHonourId(jsonObject.getLong("honourId"));
        userCurrentInfoPO.setGraduateTime(jsonObject.getString("graduateTime"));
        userCurrentInfoMapper.insertSelective(userCurrentInfoPO);

        //人卡表新增；更新card表里currNum（+1）
        //从card表里，寻找默认卡
        //人卡表里设置参数
        UserCardPO userCardPO = new UserCardPO();
        userCardPO.setTalentId(talentId);
        userCardPO.setCardId(cardId);
        //设置当前编号，组合起来，并且更新卡的currentNum
        String membershipNumber = cardPO.getInitialWord() + cardPO.getAreaNum();
        //当前编号，以后换卡需要使用
        String currentNum = "";
        Long cardPOCurrNum = CardNumberUtil.getCurrNum();
        /**
         * 看是否是尊贵的vip人才，目前保留1-10
         */
        if (cardType == 1) {
            TalentCardHoldListPO talentCardHoldListPO = talentCardHoldListMapper.selectByIdCard(idCard);
            if (talentCardHoldListPO != null) {
                cardPOCurrNum = talentCardHoldListPO.getNum();
                cardPO.setCurrNum(cardPOCurrNum - 1);
            }
        }

        //写死，长度为6
        Integer initialNumLength = 6;
        Integer currentNumLength = cardPOCurrNum.toString().length();
        //补0
        if ((initialNumLength - currentNumLength) > 0) {
            for (int i = 0; i < (initialNumLength - currentNumLength); i++) {
                membershipNumber = membershipNumber + "0";
                currentNum = currentNum + "0";
            }
        }

        membershipNumber = membershipNumber + cardPOCurrNum;
        currentNum = currentNum + cardPOCurrNum;
        userCardPO.setNum(membershipNumber);
        cardPO.setCurrNum(cardPOCurrNum + 1);
        cardPO.setWaitingMemberNum(cardPO.getWaitingMemberNum() + 1);
        //人卡表里设置参数；添加数据
        userCardPO.setCreateTime(new Date());
        userCardPO.setStatus((byte) 1);
        userCardPO.setName(cardPO.getTitle());
        /**
         * 我是标记，测试完毕后删除
         */
        TestTalentInfoPO testTalentInfoPO = testTalentInfoMapper.selectByOpenId(openId);
        if (testTalentInfoPO != null) {
            userCardPO.setNum(testTalentInfoPO.getPrimaryCardNum());
            cardPO.setCurrNum(cardPOCurrNum - 1);
        }
        /**
         * 我是结束标记，测试完毕后删除
         */

        /**
         * 不含区域号，不含前缀，单纯的000001
         */
        userCardPO.setCurrentNum(currentNum);
        int updateResult = cardMapper.updateByPrimaryKeySelective(cardPO);
        if (updateResult == 0) {
            logger.error("update cardMapper error");
        }
        userCardMapper.insertSelective(userCardPO);

        //用消息模板推送微信消息
        MessageDTO messageDTO = new MessageDTO();
        //openId
        messageDTO.setOpenid(openId);
        //开头
        messageDTO.setFirst("您好，请您领取衢江区人才卡");
        //姓名
        messageDTO.setKeyword1(talentPO.getName());
        //身份证、护照、驾照3选1，后四位变星星
        String encryptionIdCardNum = identificationCardEncryption(identificationCardNum);
        messageDTO.setKeyword2(encryptionIdCardNum);
        messageDTO.setKeyword3("个人");
        //领卡机构
        //通知时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String currentTime = formatter.format(new Date());
        messageDTO.setKeyword4(currentTime);
        //模版编号
        messageDTO.setTemplateId(1);
        //结束
        messageDTO.setRemark("领取后可享受多项人才权益哦");
        messageDTO.setUrl(FilePathConfig.getStaticPublicWxBasePath());
        MessageUtil.sendTemplateMessage(messageDTO);

        /**
         * 清除redis缓存
         */
        clearRedisCache(openId);
        if (StringUtils.isEmpty(unionId)) {
            return new ResultVO(2213);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO identification(IdentificationDTO identificationDTO) {
        /**
         * 参数列举
         */
        String openId = identificationDTO.getOpenId();
        Integer education = identificationDTO.getEducation();
        String school = identificationDTO.getSchool();
        Byte firstClass = identificationDTO.getFirstClass();
        String major = identificationDTO.getMajor();
        Integer profQualityCategory = identificationDTO.getProfQualityCategory();
        String profQualityInfo = identificationDTO.getProfQualityInfo();
        Integer profTitleCategory = identificationDTO.getProfTitleCategory();
        String profTitleInfo = identificationDTO.getProfTitleInfo();
        Long honourId = identificationDTO.getHonourId();
        String graduateTime = identificationDTO.getGraduateTime();
        String educPicture = identificationDTO.getEducPicture();
        String educPicture2 = identificationDTO.getEducPicture2();
        String educPicture3 = identificationDTO.getEducPicture3();
        String profTitlePicture = identificationDTO.getProfTitlePicture();
        String profTitlePicture2 = identificationDTO.getProfTitlePicture2();
        String profTitlePicture3 = identificationDTO.getProfTitlePicture3();
        String profQualityPicture = identificationDTO.getProfQualityPicture();
        String profQualityPicture2 = identificationDTO.getProfQualityPicture2();
        String profQualityPicture3 = identificationDTO.getProfQualityPicture3();
        String talentHonourPicture = identificationDTO.getTalentHonourPicture();
        String talentHonourPicture2 = identificationDTO.getTalentHonourPicture2();
        String talentHonourPicture3 = identificationDTO.getTalentHonourPicture3();
        Byte fullTime = identificationDTO.getFullTime();
        //人才表；通过openId获取talent表里唯一的信息
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500, "查无此人！");
        }
        Long talentId = talentPO.getTalentId();
        /**
         * 校验脏数据
         */
        //校验数据库是否存在状态3的数据
        Integer ifWaitingApproval = certificationMapper.ifWaitingApproval(openId);
        if (ifWaitingApproval != null && ifWaitingApproval != 0) {
            return new ResultVO(2308, "该用户已有待审批数据");
        }
        //校验数据库是否存在C表状态4的数据
        Integer checkIfCompleteCertificate = certificationMapper.checkIfDirty(talentId, (byte) 4, null);
        if (checkIfCompleteCertificate != 0) {
            return new ResultVO(2311, "当前有脏数据，无法发起认证");
        }
        //检验C表状态5，UC表状态2的数据是否为1条（正常情况）
        Integer checkIfDirty = certificationMapper.checkIfDirty(talentId, (byte) 5, (byte) 2);
        if (checkIfDirty != 1) {
            /**
             * 脏数据矫正为C表状态5，UC表状态2
             */
            ActivcateBO oldCard = talentMapper.activate(openId, (byte) 2, (byte) 1);
            if (oldCard == null) {
                return new ResultVO(2701);
            }
            Byte oldCardCertificationStatus = (byte) 5;
            Long oldCardCertId = oldCard.getCertId();
            certificationMapper.updateStatusByCertId(oldCardCertId, oldCardCertificationStatus);
            educationMapper.updateStatusByCertId(oldCardCertId, oldCardCertificationStatus);
            profTitleMapper.updateStatusByCertId(oldCardCertId, oldCardCertificationStatus);
            profQualityMapper.updateStatusByCertId(oldCardCertId, oldCardCertificationStatus);
            talentHonourMapper.updateStatusByCertId(oldCard.getCertId(), oldCardCertificationStatus);
            UserCardPO oldUserCardPO = userCardMapper.selectByPrimaryKey(oldCard.getUcId());
            oldUserCardPO.setStatus((byte) 2);
            userCardMapper.updateByPrimaryKeySelective(oldUserCardPO);
        }
        /**
         * 校验数据库是否存在状态9的数据
         */
        Integer ifOldCardIsBaseCard = certificationMapper.ifOldCardIsBaseCard(openId);
        if (ifOldCardIsBaseCard != null && ifOldCardIsBaseCard != 0) {
            return new ResultVO(2309, "该用户已成功审批过基本卡");
        }
        //设置状态值 状态3为认证未审批
        Byte status = (byte) 3;

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setCurrentType((byte) 4);
        certificationPO.setType((byte) 2);
        certificationMapper.add(certificationPO);
        Long certificationId = certificationPO.getCertId();

        //认证审批表
        CertApprovalPO certApprovalPO = new CertApprovalPO();
        certApprovalPO.setCertId(certificationId);
        certApprovalPO.setCreateTime(new Date());
        certApprovalPO.setType((byte) 1);
        certApprovalMapper.insertSelective(certApprovalPO);

        //认证审批记录表
        CertExamineRecordPO certExamineRecordPO = new CertExamineRecordPO();
        certExamineRecordPO.setCertId(certificationId);
        certExamineRecordPO.setCreateTime(new Date());
        certExamineRecordPO.setEducation(education);
        certExamineRecordPO.setHonourId(honourId);
        certExamineRecordPO.setPqCategory(profQualityCategory);
        certExamineRecordPO.setPtCategory(profTitleCategory);
        certExamineRecordPO.setName(talentPO.getName());
        certExamineRecordPO.setSex(talentPO.getSex());
        certExamineRecordPO.setTalentId(talentId);
        certExamineRecordPO.setResult((byte) 3);
        certExamineRecordMapper.insertSelective(certExamineRecordPO);

        //学历表
        EducationPO educationPO = new EducationPO();
        educationPO.setEducation(education);
        educationPO.setSchool(school);
        educationPO.setFirstClass(firstClass);
        educationPO.setMajor(major);
        educationPO.setEducPicture(educPicture);
        educationPO.setEducPicture2(educPicture2);
        educationPO.setEducPicture3(educPicture3);
        educationPO.setCertId(certificationId);
        educationPO.setTalentId(talentId);
        educationPO.setStatus(status);
        educationPO.setGraduateTime(graduateTime);
        educationPO.setFullTime(fullTime);
        if (education == 0) {
            //10代表本次不认证
            educationPO.setIfCertificate((byte) 10);
        } else {
            educationPO.setIfCertificate((byte) 2);
            educationMapper.insertSelective(educationPO);
        }

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCategory(profTitleCategory);
        profTitlePO.setInfo(profTitleInfo);
        profTitlePO.setPicture(profTitlePicture);
        profTitlePO.setPicture2(profTitlePicture2);
        profTitlePO.setPicture3(profTitlePicture3);
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        if (profTitleCategory == 0) {
            //10代表本次不认证
            profTitlePO.setIfCertificate((byte) 10);
        } else {
            profTitlePO.setIfCertificate((byte) 2);
            profTitleMapper.insertSelective(profTitlePO);
        }

        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCategory(profQualityCategory);
        profQualityPO.setInfo(profQualityInfo);
        profQualityPO.setPicture(profQualityPicture);
        profQualityPO.setPicture2(profQualityPicture2);
        profQualityPO.setPicture3(profQualityPicture3);
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        if (profQualityCategory == 0) {
            //10代表本次不认证
            profQualityPO.setIfCertificate((byte) 10);
        } else {
            profQualityPO.setIfCertificate((byte) 2);
            profQualityMapper.insertSelective(profQualityPO);
        }

        //人才荣誉表
        TalentHonourPO talentHonourPO = new TalentHonourPO();
        talentHonourPO.setHonourId(honourId);
        talentHonourPO.setHonourPicture(talentHonourPicture);
        talentHonourPO.setHonourPicture2(talentHonourPicture2);
        talentHonourPO.setHonourPicture3(talentHonourPicture3);
        talentHonourPO.setCertId(certificationId);
        talentHonourPO.setTalentId(talentId);
        talentHonourPO.setStatus(status);
        if (honourId == 0) {
            //10代表本次不认证
            talentHonourPO.setIfCertificate((byte) 10);
        } else {
            talentHonourPO.setIfCertificate((byte) 2);
            talentHonourMapper.insertSelective(talentHonourPO);
        }
        /**
         * 清除redis缓存
         */
        clearRedisCache(openId);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findInfo(String openId) {

        /**
         * 添加redis hash缓存
         */
        String mapStr = this.redisMapUtil.hget(openId, "findInfo");
        TalentVO cacheResult = StringToObjUtil.strToObj(mapStr, TalentVO.class);
        if (cacheResult != null) {
            return new ResultVO(1000, cacheResult);
        }

        //根据openId和c表status=9是否有数据来判断是否认证过
        //status=9说明基本卡已经作废，证明认证完成
        Integer ifCertificate = talentMapper.ifCompleteCertificate(openId);
        TalentBO talentBO;
        if (ifCertificate != 0) {
            //有作废的基础卡，说明认证通过
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("openId", openId);
            hashMap.put("status", (byte) 1);
            talentBO = talentMapper.findOne(hashMap);
            if (talentBO == null) {
                return new ResultVO(2500, "查无此人");
            }
        } else {
            //没有作废的基础卡，说明认证未通过
            talentBO = talentMapper.findRegisterOne(openId);
            if (talentBO == null) {
                return new ResultVO(2500, "查无此人");
            }
        }
        TalentVO talentVO = TalentVO.convert(talentBO);
        /**
         * 证件号后四位打星星
         */
        String idCard = talentVO.getIdCard();
        String passport = talentVO.getPassport();
        String driverCard = talentVO.getDriverCard();
        Byte cardType = talentBO.getCardType();
        if (cardType == 1) {
            idCard = identificationCardEncryption(idCard);
            talentVO.setIdCard(idCard);
        } else if (cardType == 2) {
            passport = identificationCardEncryption(passport);
            talentVO.setPassport(passport);
        } else if (cardType == 3) {
            driverCard = identificationCardEncryption(driverCard);
            talentVO.setDriverCard(driverCard);
        }

        //新增认证信息
        List<InsertCertificationBO> insertCertificationBOList = insertCertificationMapper.selectByOpenId(openId);
        talentVO = TalentVO.setInsertCertification(talentVO, insertCertificationBOList);
        /**
         * 设置缓存
         */
        this.redisMapUtil.hset(openId, "findInfo", JSON.toJSONString(talentVO));

        return new ResultVO(1000, talentVO);

    }

    /**
     * 证件号码，后四位加密
     *
     * @return
     */
    public String identificationCardEncryption(String identificationCardNum) {
        Integer end = identificationCardNum.length() - 4;
        String encryptionIdCardNum = identificationCardNum.substring(0, end) + "****";
        return encryptionIdCardNum;
    }

    /**
     * 清除用户redis缓存
     *
     * @param openId
     */
    @Override
    public void clearRedisCache(String openId) {
        /**
         * 清除redis缓存
         */
        this.redisMapUtil.del(openId);
    }

    /**
     * 获取微信用户信息，类型，会员卡号等
     *
     * @param openId
     * @return
     */
    @Override
    public TalentTypeVO getTalentInfo(String openId) {

        String redisCache = redisMapUtil.hget(openId, "getTalentInfo");
        if (!StringUtils.isEmpty(redisCache)) {
            TalentTypeVO vo = StringToObjUtil.strToObj(redisCache, TalentTypeVO.class);
            if (vo != null) {
                return vo;
            }
        }

        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            talentPO = getDefaultTalent();
            if (talentPO == null) {
                return null;
            }
        }

        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (userCurrentInfoPO == null) {
            return null;
        }

        Long cardId = talentPO.getCardId();
        String talentCategory = userCurrentInfoPO.getTalentCategory();
        Integer education = userCurrentInfoPO.getEducation();
        Integer title = userCurrentInfoPO.getPtCategory();
        Integer quality = userCurrentInfoPO.getPqCategory();
        Long talentHonour = userCurrentInfoPO.getHonourId();
        //拆分人才类别
        ArrayList categoryList = null;
        if (!StringUtils.isEmpty(talentCategory)) {
            categoryList = TalentActivityUtil.splitCategory(userCurrentInfoPO.getTalentCategory());
        }
        /**
         * 拆分学历；职称；职业资格；人才荣誉等
         */
        ArrayList educationList = null;
        ArrayList titleList = null;
        ArrayList qualityList = null;
        ArrayList honourList = null;
        String educationString = "";
        String titleString = "";
        String qualityString = "";
        String honourString = "";
        TalentCertificationInfoPO talentCertificationInfoPO =
                talentCertificationInfoMapper.selectByTalentId(talentPO.getTalentId());
        if (talentCertificationInfoPO != null) {
            educationString = talentCertificationInfoPO.getEducation();
            titleString = talentCertificationInfoPO.getPtCategory();
            qualityString = talentCertificationInfoPO.getPqCategory();
            honourString = talentCertificationInfoPO.getHonourId();
        }
        if (!StringUtils.isEmpty(educationString)) {
            educationList = TalentActivityUtil.splitCategory(educationString);
        }

        if (!StringUtils.isEmpty(titleString)) {
            titleList = TalentActivityUtil.splitCategory(titleString);
        }

        if (!StringUtils.isEmpty(qualityString)) {
            qualityList = TalentActivityUtil.splitCategory(qualityString);
        }

        if (!StringUtils.isEmpty(honourString)) {
            honourList = TalentActivityUtil.splitCategory(honourString);
        }


        TalentTypeVO vo = new TalentTypeVO();
        vo.setCardId(cardId);
        vo.setCategoryList(categoryList);
        vo.setEducation(education);
        vo.setTitle(title);
        vo.setQuality(quality);
        vo.setTalentHonour(talentHonour);
        vo.setCategory(talentCategory);

        //学历；职称；职业资格；人才荣誉
        vo.setEducationList(educationList);
        vo.setQualityList(qualityList);
        vo.setTitleList(titleList);
        vo.setHonourList(honourList);
        vo.setEducationString(educationString);
        vo.setTitleString(titleString);
        vo.setQualityString(qualityString);
        vo.setHonourString(honourString);

        redisMapUtil.hset(openId, "getTalentInfo", JSON.toJSONString(vo));
        return vo;
    }

    /**
     * 获取游客账号
     *
     * @return
     */
    private TalentPO getDefaultTalent() {
        TalentPO talentPO = null;
        String defaultTalent = redisMapUtil.hget(TalentConstant.DEFAULT_TALENT_OPENID, "getTalentInfo");
        if (!StringUtils.isEmpty(defaultTalent)) {
            talentPO = StringToObjUtil.strToObj(defaultTalent, TalentPO.class);
        }
        if (talentPO == null) {
            talentPO = talentMapper.selectByOpenId(TalentConstant.DEFAULT_TALENT_OPENID);
            redisMapUtil.hset(TalentConstant.DEFAULT_TALENT_OPENID, "getTalentInfo", JSON.toJSONString(talentPO));
        }
        return talentPO;
    }

    @Override
    public ResultVO fillUnion() {
        List<TalentPO> pos = talentMapper.queryAllNullUnionId();
        if (null != pos && pos.size() > 0) {
            for (TalentPO po : pos) {
                String unionId = getUnionIdByOpenId(po.getOpenId());
                if (!StringUtils.isEmpty(unionId)) {
                    po.setUnionId(unionId);
                    // talent表中unionId 为 null 的数据较少，故直接使用单条数据插入
                    talentMapper.updateByPrimaryKey(po);
                }
            }
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO updateUnionId(String token, String openId) {

        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO != null) {
            if (StringUtils.isEmpty(talentPO.getUnionId())) {
                String unionId = getUserInfoUnionId(token, openId);
                if (!StringUtils.isEmpty(unionId)) {
                    talentPO.setUnionId(unionId);
                    talentMapper.updateByPrimaryKeySelective(talentPO);
                }
            }
        }
        return new ResultVO(1000);
    }

    public String getUnionIdByOpenId(String openId) {
        String userInfo = new RestTemplate().getForObject("https://api.weixin.qq.com/cgi-bin/user/info?access_token={1}&openid={2}&lang=zh_CN",
                String.class, AccessTokenUtil.getAccessToken(), openId);

        if (logger.isDebugEnabled()) {
            logger.info("getUnionIdByOpenId openId:{} result:{}", openId, userInfo);
        }
        return JSONObject.parseObject(userInfo).getString("unionid");
    }


    public String getUserInfoUnionId(String access_token, String openId) {

        String userInfo = new RestTemplate().getForObject("https://api.weixin.qq.com/sns/userinfo?access_token={1}&openid={2}&lang=zh_CN",
                String.class, access_token, openId);
        if (logger.isDebugEnabled()) {
            logger.info("getUserInfoUnionId openId:{} result:{}", openId, userInfo);
        }
        return JSONObject.parseObject(userInfo).getString("unionid");

    }
}
