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
import com.talentcard.front.utils.TalentUtil;
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
    public ResultVO<TalentPO> findStatus(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(5555, "查无此人");
        }
        return new ResultVO(1000, talentPO);
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
        talentPO.setIndustry(jsonObject.getString("industry"));
        talentPO.setPhone(jsonObject.getString("phone"));
        talentPO.setCreateTime(new Date());
        talentPO.setStatus(status);
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

        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(HashMap<String, Object> hashMap) {
        TalentBO talentBO = talentMapper.findOne(hashMap).get(0);
        //学历
        for (EducationPO educationPO : talentBO.getEducationPOList()) {
            if (educationPO.getEducPicture() != null) {
                educationPO.setEducPicture(publicPath + educationPO.getEducPicture());
            }
        }
        //职称
        for (ProfTitlePO profTitlePO : talentBO.getProfTitlePOList()) {
            if (profTitlePO.getPicture() != null) {
                profTitlePO.setPicture(publicPath + profTitlePO.getPicture());
            }
        }
        //职业资格
        for (ProfQualityPO profQualityPO : talentBO.getProfQualityPOList()) {
            if (profQualityPO.getPicture() != null) {
                profQualityPO.setPicture(publicPath + profQualityPO.getPicture());
            }
        }
        return new ResultVO(1000, talentBO);
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
        String educUrl = FileUtil.uploadFile
                (educPicture, rootDir, projectDir, educationDir, "education");
        String profTitleUrl = FileUtil.uploadFile
                (profTitlePicture, rootDir, projectDir, profTitleDir, "profTitle");
        String profQualityUrl = FileUtil.uploadFile
                (profQualityPicture, rootDir, projectDir, profQualityDir, "profQuality");
        if (educUrl == "" || profTitleUrl == "" || profQualityUrl == "") {
            return new ResultVO(2304, "上传文件失败");
        }

        //人才表；通过openId获取talent表里唯一的信息
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        talentPO.setStatus(status);
        talentMapper.updateByPrimaryKeySelective(talentPO);
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
    @Transactional(rollbackFor = Exception.class)
    public ResultVO activate(String openId, String code) {
        //判断人卡表里是否已经有待审批的，如果有，错误代码
        Integer ifExist = userCardMapper.findUserCardExist(openId);
        if (ifExist != 0) {
            return new ResultVO(2310);
        }

        //从card表里，寻找默认卡
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        CardPO cardPO = cardMapper.findDefaultCard();
        Long cardId = cardPO.getCardId();
        jsonObject.put("cardId", cardId);
        //更新人才表的cardId
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        talentPO.setCardId(cardId);
        talentMapper.updateByPrimaryKeySelective(talentPO);
        //人卡表里设置参数
        UserCardPO userCardPO = new UserCardPO();
        userCardPO.setTalentId(talentPO.getTalentId());
        userCardPO.setCardId(cardId);
        //设置当前编号，组合起来，并且更新卡的currentNum
        String membershipNumber = cardPO.getInitialWord() + cardPO.getCurrNum();
        userCardPO.setNum(membershipNumber);
        jsonObject.put("membershipNumber", membershipNumber);
        cardPO.setCurrNum(cardPO.getCurrNum() + 1);
        cardMapper.updateByPrimaryKeySelective(cardPO);
        //人卡表里设置参数；添加数据
        userCardPO.setCreateTime(new Date());
        userCardPO.setStatus((byte) 1);
        userCardMapper.insertSelective(userCardPO);
        //发送post请求，激活卡套
//        try {
//            String accessToken = AccessTokenUtil.getAccessToken();
//            String url = "https://api.weixin.qq.com/card/membercard/activate?access_token=" + accessToken;
//            WechatApiUtil.postRequest(url, jsonObject);
//        } catch (WechatException wechatException) {
//            return new ResultVO(6666);
//        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findRegisterOne(String openId) {
        return new ResultVO(1000, talentMapper.findRegisterOne(openId));
    }
}
