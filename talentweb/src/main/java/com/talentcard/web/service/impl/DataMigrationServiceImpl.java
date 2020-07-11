package com.talentcard.web.service.impl;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.mapper.CertExamineRecordMapper;
import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.mapper.TalentCertificationInfoMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.CertApprovalPO;
import com.talentcard.common.pojo.CertExamineRecordPO;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IDataMigrationService;
import com.talentcard.web.vo.TalentVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-11 15:52
 * @description
 */
@Service
public class DataMigrationServiceImpl implements IDataMigrationService {
    @Autowired
    CertificationMapper certificationMapper;
    @Autowired
    CertExamineRecordMapper certExamineRecordMapper;
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private TalentCertificationInfoMapper talentCertificationInfoMapper;


    @Override
    public ResultVO certExamineRecord() {
        HashMap<String, Object> hashMap = new HashMap<>(9);
        hashMap.put("name", null);
        hashMap.put("sex", null);
        hashMap.put("pqCategory", null);
        hashMap.put("ptCategory", null);
        hashMap.put("education", null);
        hashMap.put("result", null);
        hashMap.put("startTime", null);
        hashMap.put("endTime", null);
        hashMap.put("honour", null);
        List<TalentCertStatusBO> bos = certificationMapper.queryAllCert(hashMap);
        CertExamineRecordPO certExamineRecordPO;
        //失败结果
        ArrayList<CertExamineRecordPO> failureList = new ArrayList();
        //重复结果
        ArrayList<CertExamineRecordPO> duplicateList = new ArrayList();
        Integer insertResult;
        for (TalentCertStatusBO talentCertStatusBO : bos) {
            if (talentCertStatusBO == null) {
                continue;
            }
            //如果存在记录，则跳过，说明该条certId的记录已在新表之中。
            certExamineRecordPO = null;
            certExamineRecordPO = certExamineRecordMapper.selectByCertId(talentCertStatusBO.getCertId());
            if (certExamineRecordPO != null) {
                duplicateList.add(certExamineRecordPO);
                continue;
            }
            certExamineRecordPO = new CertExamineRecordPO();
            certExamineRecordPO.setResult(talentCertStatusBO.getResult());
            certExamineRecordPO.setTalentId(talentCertStatusBO.getTalentId());
            certExamineRecordPO.setSex(talentCertStatusBO.getSex());
            certExamineRecordPO.setName(talentCertStatusBO.getName());
            certExamineRecordPO.setPtCategory(talentCertStatusBO.getPtCategory());
            certExamineRecordPO.setPqCategory(talentCertStatusBO.getPqCategory());
            certExamineRecordPO.setHonourId(talentCertStatusBO.getHonour());
            certExamineRecordPO.setEducation(talentCertStatusBO.getEducation());
            certExamineRecordPO.setCreateTime(talentCertStatusBO.getCreateTime());
            certExamineRecordPO.setCertId(talentCertStatusBO.getCertId());
            insertResult = 0;
            insertResult = certExamineRecordMapper.insertSelective(certExamineRecordPO);
            if (insertResult == 0) {
                failureList.add(certExamineRecordPO);
            }
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("failureList", failureList);
        result.put("duplicateList", duplicateList);
        return new ResultVO(1000, result);
    }

    @Override
    public ResultVO certTalent() {
        Map<String, Object> reqMap = new HashMap<>(10);
        reqMap.put("start", null);
        reqMap.put("end", null);
        reqMap.put("name", null);
        reqMap.put("sex", null);
        reqMap.put("educ", null);
        reqMap.put("title", null);
        reqMap.put("title", null);
        reqMap.put("quality", null);
        reqMap.put("honour", null);
        reqMap.put("card", null);
        reqMap.put("category", null);
        List<TalentBO> bos = talentMapper.queryCert(reqMap);

        TalentCertificationInfoPO talentCertificationInfoPO;
        //失败结果
        ArrayList<TalentCertificationInfoPO> failureList = new ArrayList();
        //重复结果
        ArrayList<TalentCertificationInfoPO> duplicateList = new ArrayList();
        Integer insertResult;

        for (TalentBO talentBO : bos) {
            if (talentBO == null) {
                continue;
            }
            //如果存在记录，则跳过，说明该条certId的记录已在新表之中。
            talentCertificationInfoPO = null;
            talentCertificationInfoPO = talentCertificationInfoMapper.selectByTalentId(talentBO.getTalentId());
            if (talentCertificationInfoPO != null) {
                duplicateList.add(talentCertificationInfoPO);
                continue;
            }
            talentCertificationInfoPO = new TalentCertificationInfoPO();
            //学历
            if (talentBO.getEduc() != null) {
                talentCertificationInfoPO.setEducation(talentBO.getEduc().toString());
            } else {
                talentCertificationInfoPO.setEducation("0");
            }
            //职称
            if (talentBO.getTitle() != null) {
                talentCertificationInfoPO.setPtCategory(talentBO.getTitle().toString());
            } else {
                talentCertificationInfoPO.setPtCategory("0");
            }
            //职业资格
            if (talentBO.getQuality() != null) {
                talentCertificationInfoPO.setPqCategory(talentBO.getQuality().toString());
            } else {
                talentCertificationInfoPO.setPqCategory("0");
            }
            //人才荣誉
            if (talentBO.getHonour() != null) {
                talentCertificationInfoPO.setHonourId(talentBO.getHonour().toString());
            } else {
                talentCertificationInfoPO.setHonourId("");
            }
            //人才类别
            talentCertificationInfoPO.setTalentCategory(talentBO.getCategory());
            insertResult = talentCertificationInfoMapper.insertSelective(talentCertificationInfoPO);
            insertResult = 0;
            if (insertResult != 0) {
                failureList.add(talentCertificationInfoPO);
            }
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("failureList", failureList);
        result.put("duplicateList", duplicateList);
        return new ResultVO(1000, result);
    }
}
