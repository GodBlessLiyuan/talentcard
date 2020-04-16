package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.web.vo.ApprovalItemsVO;
import com.talentcard.web.vo.ConfirmMsgVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertApprocalService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:31 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Service
public class CertApprocalServiceImpl implements ICertApprocalService {
    @Resource
    CertApprovalMapper certApprovalMapper;
    @Resource
    CertificationMapper certificationMapper;
    @Resource
    TalentMapper talentMapper;
    @Resource
    EducationMapper educationMapper;
    @Resource
    ProfQualityMapper profQualityMapper;
    @Resource
    ProfTitleMapper profTitleMapper;
    @Resource
    UserCurrentInfoMapper userCurrentInfoMapper;

    /**
     * 审批result的值含义
     */
    public static final Byte SUCCESS = 1;
    public static final Byte FAILURE = 2;


    @Override
    public ResultVO certApprovalShowItems(Long talentId) {
        ApprovalBO bo = certificationMapper.queryAllMsg(talentId);
        List<CertApprovalPO> pos = certApprovalMapper.queryApprovalById(talentId);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);
        return new ResultVO(1000, approvalItemsVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO confirmCert(HttpSession session, Map<String, Object> reqData) {
        // 判断审批结果，如果审批通过，需要多表更新；审批不通过则只新增认证审批表的信息
        //首先获取审批人的用户id
        Long userId = (Long) session.getAttribute("userId");
        Byte result = (Byte) reqData.get("result");
        Long certId = (Long) reqData.get("certId");
        Long talentId = (Long) reqData.get("talentId");
        // 2 代表审批驳回
        //(1) 新增认证审批表
        CertApprovalPO certApprovalPo = new CertApprovalPO();
        certApprovalPo.setCertId(certId);
        //将审批类型更新到2审批
        certApprovalPo.setCreateTime((Date) reqData.get("createTime"));
        certApprovalPo.setType((byte) 2);
        certApprovalPo.setCategory((String) reqData.get("category"));
        certApprovalPo.setUserId(userId);
        certApprovalPo.setOpinion((String) reqData.get("opinion"));
        certApprovalPo.setUpdateTime(new Date());
        if (result.equals(FAILURE)) {
            certApprovalPo.setResult(FAILURE);
            int insertResult = certApprovalMapper.insertSelective(certApprovalPo);
            if (insertResult == 0) {
                //新增认证审批表失败
                return new ResultVO(2365);
            }
            // 若审批拒绝，则将当前认证表设置为失效状态
            CertificationPO certificationPo = new CertificationPO();
            certificationPo.setCertId(certId);
            certificationPo.setStatus((byte)6);
            int resultUpdateCertification = certificationMapper.updateByPrimaryKeySelective(certificationPo);
            if (resultUpdateCertification == 0) {
                //更新认证审批表失败
                return new ResultVO(2372);
            }

        }else {
            certApprovalPo.setResult(SUCCESS);
            certApprovalPo.setCardId((Long) reqData.get("cardId"));
            int insertResult = certApprovalMapper.insertSelective(certApprovalPo);
            if (insertResult == 0) {
                //新增认证审批表失败
                return new ResultVO(2365);
            }
            // (2) 更新认证表
            CertificationPO certificationPo = new CertificationPO();
            certificationPo.setCertId(certId);
            certificationPo.setStatus((byte)1);
            int resultUpdateCertification = certificationMapper.updateByPrimaryKeySelective(certificationPo);
            if (resultUpdateCertification == 0) {
                //更新认证审批表失败
                return new ResultVO(2366);
            }

            //(3) 更新人才表
            TalentPO talentPO = new TalentPO();
            talentPO.setTalentId(talentId);
            talentPO.setStatus((byte)4);
            talentPO.setCardId(certId);
            talentPO.setCategory((String) reqData.get("category"));
            int resultUpdateTalent = talentMapper.updateByPrimaryKeySelective(talentPO);
            if (resultUpdateTalent == 0) {
                //更新人才表失败
                return new ResultVO(2367);
            }
            //(4) 更新学历表的认证状态
            int resultEducation = educationMapper.updateStatusByCertId(certId, (byte) 1);
            if (resultEducation == 0) {
                //更新学历表状态失败
                return new ResultVO(2368);
            }
            //(5) 更新职称表的认证状态
            int resultProfTitle = profTitleMapper.updateStatusByCertId(certId, (byte) 1);
            if (resultProfTitle == 0) {
                //更新职称表状态失败
                return new ResultVO(2369);
            }
            //(6) 更新职业资格表的认证状态
            int resultProfQuality = profQualityMapper.updateStatusByCertId(certId,(byte) 1);
            if (resultProfQuality == 0) {
                //更新职称表状态失败
                return new ResultVO(2370);
            }
            //(7) 更新用户当前最新的基本信息
            int resultUserInfo = userCurrentInfoMapper.updateCategoryByTalentId(talentId,(String) reqData.get("category"));
            if (resultUserInfo == 0) {
                //更新用户当前最新信息状态失败
                return new ResultVO(2371);
            }
        }
        return new ResultVO(1000);
    }
}
