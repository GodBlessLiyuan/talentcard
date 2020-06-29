package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.bo.InsertCertApprovalBO;
import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IInsertCertificationService;
import com.talentcard.web.service.ITalentService;
import com.talentcard.web.vo.InsertCertificationVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-24 10:38
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
    private CardMapper cardMapper;
    @Autowired
    private UserCurrentInfoMapper userCurrentInfoMapper;
    @Autowired
    ITalentService iTalentService;


    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<InsertCertificationBO> insertCertificationBOList = insertCertificationMapper.query(hashMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), insertCertificationBOList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO certResult(HttpSession httpSession, Long talentId, Long insertCertId, Byte result, String opinion) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null || talentPO.getDr() == 2) {
            return new ResultVO(2500);
        }
        String openId = talentPO.getOpenId();
        InsertCertificationPO insertCertificationPO = insertCertificationMapper.selectByPrimaryKey(insertCertId);
        if (insertCertificationPO == null) {
            return new ResultVO(2551);
        }
        Byte status;
        if (result == 1) {
            //通过
            status = 1;
        } else {
            //驳回
            status = 3;
        }
        insertCertificationPO.setStatus(status);
        insertCertificationMapper.updateByPrimaryKeySelective(insertCertificationPO);

        //审批表
        InsertCertApprovalPO insertCertApprovalPO = new InsertCertApprovalPO();
        insertCertApprovalPO.setType((byte) 2);
        insertCertApprovalPO.setDr((byte) 1);
        insertCertApprovalPO.setCreateTime(new Date());
        insertCertApprovalPO.setUpdateTime(new Date());
        insertCertApprovalPO.setResult(result);
        insertCertApprovalPO.setUserId((Long) httpSession.getAttribute("userId"));
        insertCertApprovalPO.setOpinion(opinion);
        insertCertApprovalPO.setInsertCertId(insertCertId);
        insertCertApprovalMapper.insertSelective(insertCertApprovalPO);

        /**
         * 链接学历职称职业资格人才荣誉
         */
        ActivcateBO activcateBO = talentMapper.activate(openId, (byte) 1, (byte) 2);
        if (activcateBO == null) {
            return new ResultVO(2900, "新增审批时，人才状态不对！");
        }
        InsertCertificationBO insertCertificationBO = insertCertificationMapper.findOne(openId, insertCertId);
        Long certId = activcateBO.getCertId();
        Byte type = insertCertificationBO.getType();
        if (type == 1) {
            //学历
            EducationPO educationPO = new EducationPO();
            InsertEducationPO insertEducationPO = insertCertificationBO.getInsertEducationPO();
            if (insertEducationPO == null) {
                return new ResultVO(2556);
            }
            //修改新增状态
            insertEducationPO.setStatus(status);
            insertEducationMapper.updateByPrimaryKeySelective(insertEducationPO);

            //审批通过，链接认证表
            if (status == 1) {
                educationPO.setGraduateTime(insertEducationPO.getGraduateTime());
                educationPO.setFirstClass(insertEducationPO.getFirstClass());
                educationPO.setEducPicture(insertEducationPO.getEducPicture());
                educationPO.setSchool(insertEducationPO.getSchool());
                educationPO.setMajor(insertEducationPO.getMajor());
                educationPO.setEducation(insertEducationPO.getEducation());
                educationPO.setIfCertificate((byte) 1);
                educationPO.setCertId(certId);
                educationPO.setStatus((byte) 1);
                educationPO.setTalentId(talentId);
                educationMapper.insertSelective(educationPO);
            }
        } else if (type == 2) {
            //职称
            ProfTitlePO profTitlePO = new ProfTitlePO();
            InsertTitlePO insertTitlePO = insertCertificationBO.getInsertTitlePO();
            if (insertTitlePO == null) {
                return new ResultVO(2556);
            }
            //修改新增状态
            insertTitlePO.setStatus(status);
            insertTitleMapper.updateByPrimaryKeySelective(insertTitlePO);

            //审批通过，链接认证表
            if (status == 1) {
                profTitlePO.setPicture(insertTitlePO.getPicture());
                profTitlePO.setInfo(insertTitlePO.getInfo());
                profTitlePO.setCategory(insertTitlePO.getCategory());
                profTitlePO.setCertId(certId);
                profTitlePO.setStatus((byte) 1);
                profTitlePO.setTalentId(talentId);
                profTitlePO.setIfCertificate((byte) 1);
                profTitleMapper.insertSelective(profTitlePO);
            }
        } else if (type == 3) {
            //职业资格
            ProfQualityPO profQualityPO = new ProfQualityPO();
            InsertQualityPO insertQualityPO = insertCertificationBO.getInsertQualityPO();
            if (insertQualityPO == null) {
                return new ResultVO(2556);
            }
            //修改新增状态
            insertQualityPO.setStatus(status);
            insertQualityMapper.updateByPrimaryKeySelective(insertQualityPO);

            //审批通过，链接认证表
            if (status == 1) {
                profQualityPO.setPicture(insertQualityPO.getPicture());
                profQualityPO.setStatus((byte) 1);
                profQualityPO.setTalentId(talentId);
                profQualityPO.setCertId(certId);
                profQualityPO.setInfo(insertQualityPO.getInfo());
                profQualityPO.setCategory(insertQualityPO.getCategory());
                profQualityPO.setIfCertificate((byte) 1);
                profQualityMapper.insertSelective(profQualityPO);
            }
        } else {
            //人才荣誉
            TalentHonourPO talentHonourPO = new TalentHonourPO();
            InsertHonourPO insertHonourPO = insertCertificationBO.getInsertHonourPO();
            if (insertHonourPO == null) {
                return new ResultVO(2556);
            }
            //修改新增状态
            insertHonourPO.setStatus(status);
            insertHonourMapper.updateByPrimaryKeySelective(insertHonourPO);

            //审批通过，链接认证表
            if (status == 1) {
                talentHonourPO.setInfo(insertHonourPO.getInfo());
                talentHonourPO.setHonourPicture(insertHonourPO.getHonourPicture());
                talentHonourPO.setHonourId(insertHonourPO.getHonourId());
                talentHonourPO.setCertId(certId);
                talentHonourPO.setIfCertificate((byte) 1);
                talentHonourPO.setTalentId(talentId);
                talentHonourPO.setStatus((byte) 1);
                talentHonourMapper.insertSelective(talentHonourPO);
            }
        }
        /**
         * 清除redis缓存
         */
        iTalentService.clearRedisCache(talentPO.getOpenId());
        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(Long talentId, Long insertCertId) {
        HashMap<String, Object> result = new HashMap<>(5);
        //基本信息
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        //新增认证信息
        InsertCertificationBO insertCertificationBO = insertCertificationMapper.findOne(talentPO.getOpenId(), insertCertId);
        if (insertCertificationBO == null) {
            return new ResultVO(2551);
        }
        InsertCertificationVO insertCertificationVO = InsertCertificationVO.convert(insertCertificationBO);
        //记录信息
        List<InsertCertApprovalBO> insertCertApprovalBOList = insertCertApprovalMapper.findRecord(talentId);
        //卡名和人才类别
//        CardPO cardPO = cardMapper.selectByPrimaryKey(talentPO.getCardId());
//        if (cardPO == null) {
//            return new ResultVO(2600);
//        }
//        String cardName = cardPO.getTitle();
//        if (!StringUtils.isEmpty(cardPO.getInitialWord())) {
//            cardName = cardName + cardPO.getInitialWord();
//        }
//        UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByTalentId(talentId);
//        if (userCurrentInfoPO == null) {
//            return new ResultVO(2500);
//        }
        result.put("basicInfo", talentPO);
        result.put("insertCertInfo", insertCertificationVO);
        result.put("record", insertCertApprovalBOList);
        return new ResultVO(1000, result);
    }
}
