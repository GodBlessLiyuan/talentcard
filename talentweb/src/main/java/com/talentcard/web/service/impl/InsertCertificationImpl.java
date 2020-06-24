package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.InsertCertApprovalBO;
import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.BatchCertificatePO;
import com.talentcard.common.pojo.InsertCertApprovalPO;
import com.talentcard.common.pojo.InsertCertificationPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IInsertCertificationService;
import com.talentcard.web.vo.InsertCertificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Service;

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


    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<TalentBO> page = PageHelper.startPage(pageNum, pageSize);
        List<InsertCertificationBO> insertCertificationBOList = insertCertificationMapper.query(hashMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), insertCertificationBOList));
    }

    @Override
    public ResultVO certResult(HttpSession httpSession, Long talentId, Long insertCertId, Byte result, String opinion) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null || talentPO.getDr() == 2) {
            return new ResultVO(2500);
        }
        InsertCertificationPO insertCertificationPO = insertCertificationMapper.selectByPrimaryKey(insertCertId);
        if (insertCertificationPO == null) {
            return new ResultVO(2521);
        }
        if (result == 1) {
            insertCertificationPO.setStatus((byte) 1);
        } else {
            insertCertificationPO.setStatus((byte) 3);
        }
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

        return new ResultVO(1000);
    }

    @Override
    public ResultVO findOne(Long talentId, Long insertCertId) {
        HashMap<String, Object> result = new HashMap<>();
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        InsertCertificationBO insertCertificationBO = insertCertificationMapper.findOne(talentPO.getOpenId(), insertCertId);
        if (insertCertificationBO == null) {
            return new ResultVO(2551);
        }
        InsertCertificationVO insertCertificationVO = InsertCertificationVO.convert(insertCertificationBO);

        InsertCertApprovalBO insertCertApprovalBO = insertCertApprovalMapper.findRecord(talentId);
        result.put("basicInfo", talentPO);
        result.put("insertCertInfo", insertCertificationVO);
        result.put("record", insertCertApprovalBO);
        return new ResultVO(1000, result);
    }
}
