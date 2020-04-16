package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.web.vo.ApprovalItemsVO;
import com.talentcard.web.vo.ConfirmMsgVO;
import com.talentcard.common.mapper.CertApprovalMapper;
import com.talentcard.common.mapper.CertificationMapper;
import com.talentcard.common.pojo.CertApprovalPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICertApprocalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
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

    public static final Byte FAILURE = 2;



    @Override
    public ResultVO certApprovalShowItems(Long talentId){
        ApprovalBO bo = certificationMapper.queryAllMsg(talentId);
        List<CertApprovalPO> pos = certApprovalMapper.queryApprovalById(talentId);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);

        return new ResultVO(1000, approvalItemsVO);
    }

    @Override
    public ResultVO confirmCert(HttpSession session, Map<String, Object> reqData) {
        // 判断审批结果，如果审批通过，需要多表更新；审批不通过则只新增认证审批表的信息
        //首先获取审批人的用户id
        Long userId = (Long) session.getAttribute("userId");
        Byte result = (Byte)reqData.get("result");
        // 2 代表审批驳回
        if (result.equals(FAILURE)) {
            //新增认证审批表
            CertApprovalPO certApprovalPo = new CertApprovalPO();
            certApprovalPo.setCardId((Long)reqData.get("certId"));
            //将审批类型更新到2审批
            certApprovalPo.setType((byte)2);
            certApprovalPo.setCategory((String)reqData.get("category"));
            certApprovalPo.setUserId(userId);

        }else{

        }





        return new ResultVO(1000);
    }
}
