package com.talentcard.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.ITalentService;
import com.talentcard.front.utils.AccessTokenUtil;
import com.talentcard.front.utils.MessageUtil;
import com.talentcard.front.utils.TalentUtil;
import com.talentcard.front.vo.TalentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;

/**
 * 人才注册/认证相关
 *
 * @author ChenXU
 */
@EnableTransactionManagement
@Service
public class TalentServiceImpl implements ITalentService {
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
    private CertApprovalMapper certApprovalMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;

    @Value("${file.publicPath}")
    private String publicPath;
    @Value("${file.rootDir}")
    private String rootDir;
    @Value("${file.projectDir}")
    private String projectDir;
    @Value("${file.educationDir}")
    private String educationDir;
    @Value("${file.profTitleDir}")
    private String profTitleDir;
    @Value("${file.profQualityDir}")
    private String profQualityDir;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<TalentPO> findStatus(String openId) {
        //待领取的卡
        HashMap<String, Object> getCard = userCardMapper.findCurrentCard(openId, (byte) 1);
        //正在使用的卡
        HashMap<String, Object> currentCard = userCardMapper.findCurrentCard(openId, (byte) 2);
        HashMap<String, Object> result = new HashMap(4);
        if (getCard == null && currentCard == null) {
            //都为空，说明这个人没有卡，需要注册
            result.put("status", 2);
        } else if (getCard != null) {
            //说明存在待领取的卡，优先给待领取的
            result.put("status", 1);
            result.put("cardId", getCard.get("cardId"));
            result.put("code", getCard.get("code"));
        } else {
            //实在不行，给正在使用的卡
            result.put("status", 1);
            result.put("cardId", currentCard.get("cardId"));
            result.put("code", currentCard.get("code"));
        }
        Integer ifChangeCard = talentMapper.ifExistGetCard(openId);
        if (ifChangeCard == 0) {
            result.put("ifChangeCard", 2);
        } else {
            result.put("ifChangeCard", 1);
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
            return new ResultVO(2305);
        }
        //设置状态值 状态3为注册中
        Byte status = (byte) 2;
        //通过currentType判定第一次注册填写的哪一个
        Byte currentType = jsonObject.getByte("currentType");
        //人才表
        TalentPO talentPO = new TalentPO();
        talentPO.setOpenId(openId);
        talentPO.setName(jsonObject.getString("name"));
        //通过身份证号判断性别
        String idCard = jsonObject.getString("idCard");
        talentPO.setSex(TalentUtil.judgeGenderUtil(idCard));
        talentPO.setIdCard(idCard);
        talentPO.setPassport(jsonObject.getString("passport"));
        talentPO.setWorkUnit(jsonObject.getString("workUnit"));
        talentPO.setIndustry(jsonObject.getInteger("industry"));
        talentPO.setPhone(jsonObject.getString("phone"));
        talentPO.setCreateTime(new Date());
        talentPO.setStatus(status);
        //人才表的初级卡cardId
        CardPO cardPO = cardMapper.findDefaultCard();
        Long cardId = cardPO.getCardId();
        talentPO.setCardId(cardId);
        talentMapper.add(talentPO);
        Long talentId = talentPO.getTalentId();

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setCurrentType(currentType);
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
        educationMapper.insertSelective(educationPO);

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCategory(jsonObject.getInteger("profTitleCategory"));
        profTitlePO.setInfo(jsonObject.getString("profTitleInfo"));
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        profTitleMapper.insertSelective(profTitlePO);


        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCategory(jsonObject.getInteger("profQualityCategory"));
        profQualityPO.setInfo(jsonObject.getString("profQualityInfo"));
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        profQualityMapper.insertSelective(profQualityPO);

        //更新基本信息表
        UserCurrentInfoPO userCurrentInfoPO = new UserCurrentInfoPO();
        userCurrentInfoPO.setTalentId(talentId);
        userCurrentInfoPO.setEducation(jsonObject.getInteger("education"));
        userCurrentInfoPO.setPtCategory(jsonObject.getInteger("profTitleCategory"));
        userCurrentInfoPO.setPtInfo(jsonObject.getString("profTitleInfo"));
        userCurrentInfoPO.setPqCategory(jsonObject.getInteger("profQualityCategory"));
        userCurrentInfoPO.setPqInfo(jsonObject.getString("profQualityInfo"));
        userCurrentInfoPO.setPolitical((byte) 0);
        userCurrentInfoMapper.insertSelective(userCurrentInfoPO);

        //人卡表新增；更新card表里currNum（+1）
        //从card表里，寻找默认卡
        //人卡表里设置参数
        UserCardPO userCardPO = new UserCardPO();
        userCardPO.setTalentId(talentId);
        userCardPO.setCardId(cardId);
        //设置当前编号，组合起来，并且更新卡的currentNum
        String membershipNumber = cardPO.getInitialWord();
        Integer initialNumLength = cardPO.getInitialNum().length();
        Integer currentNumlength = cardPO.getCurrNum().toString().length();
        //补0
        if ((initialNumLength - currentNumlength) > 0) {
            for (int i = 0; i < (initialNumLength - currentNumlength); i++) {
                membershipNumber = membershipNumber + "0";
            }
        }
        membershipNumber = membershipNumber + cardPO.getCurrNum();
        userCardPO.setNum(membershipNumber);
        cardPO.setCurrNum(cardPO.getCurrNum() + 1);
        cardPO.setMemberNum(cardPO.getMemberNum() + 1);
        //人卡表里设置参数；添加数据
        userCardPO.setCreateTime(new Date());
        userCardPO.setStatus((byte) 1);
        cardMapper.updateByPrimaryKeySelective(cardPO);
        userCardMapper.insertSelective(userCardPO);
        MessageUtil.sendTemplateMessage(openId);
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO identification(String openId,
                                   Byte political,
                                   Integer education,
                                   String school,
                                   Byte firstClass,
                                   String major,
                                   Integer profQualityCategory,
                                   String profQualityInfo,
                                   Integer profTitleCategory,
                                   String profTitleInfo,
                                   MultipartFile educPicture,
                                   MultipartFile profTitlePicture,
                                   MultipartFile profQualityPicture) {
        //设置状态值 状态3为认证未审批
        Byte status = (byte) 3;
        //上传文件
        String educUrl = "";
        String profTitleUrl = "";
        String profQualityUrl = "";
        if (educPicture != null) {
            educUrl = FileUtil.uploadFile
                    (educPicture, rootDir, projectDir, educationDir, "education");
        }
        if (profTitlePicture != null) {
            profTitleUrl = FileUtil.uploadFile
                    (profTitlePicture, rootDir, projectDir, profTitleDir, "profTitle");
        }
        if (profQualityPicture != null) {
            profQualityUrl = FileUtil.uploadFile
                    (profQualityPicture, rootDir, projectDir, profQualityDir, "profQuality");
        }
        if (educUrl == "" && profTitleUrl == "" && profQualityUrl == "") {
            return new ResultVO(2304, "上传文件失败");
        }

        //人才表；通过openId获取talent表里唯一的信息
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        Long talentId = talentPO.getTalentId();

        //认证表
        CertificationPO certificationPO = new CertificationPO();
        certificationPO.setPolitical(political);
        certificationPO.setCreateTime(new Date());
        certificationPO.setStatus(status);
        certificationPO.setTalentId(talentId);
        certificationPO.setCurrentType((byte) 4);
        certificationMapper.add(certificationPO);
        Long certificationId = certificationPO.getCertId();

        //认证审批表
        CertApprovalPO certApprovalPO = new CertApprovalPO();
        certApprovalPO.setCertId(certificationId);
        certApprovalPO.setCreateTime(new Date());
        certApprovalPO.setType((byte) 1);
        certApprovalMapper.insertSelective(certApprovalPO);

        //学历表
        EducationPO educationPO = new EducationPO();
        educationPO.setEducation(education);
        educationPO.setSchool(school);
        educationPO.setFirstClass(firstClass);
        educationPO.setMajor(major);
        educationPO.setEducPicture(educUrl);
        educationPO.setCertId(certificationId);
        educationPO.setTalentId(talentId);
        educationPO.setStatus(status);
        educationMapper.insertSelective(educationPO);

        //职称表
        ProfTitlePO profTitlePO = new ProfTitlePO();
        profTitlePO.setCategory(profTitleCategory);
        profTitlePO.setInfo(profTitleInfo);
        profTitlePO.setPicture(profTitleUrl);
        profTitlePO.setCertId(certificationId);
        profTitlePO.setTalentId(talentId);
        profTitlePO.setStatus(status);
        profTitleMapper.insertSelective(profTitlePO);


        //职业资格表
        ProfQualityPO profQualityPO = new ProfQualityPO();
        profQualityPO.setCategory(profQualityCategory);
        profQualityPO.setInfo(profQualityInfo);
        profQualityPO.setPicture(profQualityUrl);
        profQualityPO.setCertId(certificationId);
        profQualityPO.setTalentId(talentId);
        profQualityPO.setStatus(status);
        profQualityMapper.insertSelective(profQualityPO);

        return new ResultVO(1000);
    }

    @Override
    public ResultVO findInfo(String openId) {
        //根据openId和c表status=9是否有数据来判断是否认证过
        //status=9说明基本卡已经作废，证明认证完成
        Integer ifCertificate = talentMapper.ifCertificate(openId);
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
        return new ResultVO(1000, talentVO);

    }
}
