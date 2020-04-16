package com.talentcard.web.service.impl;

import com.talentcard.common.bo.ApprovalBO;
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

    @Override
    public ResultVO certApprovalShowItems(String name){
        ApprovalBO bo = certificationMapper.queryAllMsg(name);
        List<CertApprovalPO> pos = certApprovalMapper.queryApprovalByName(name);
        ApprovalItemsVO approvalItemsVO = new ApprovalItemsVO();
        approvalItemsVO.setApprovalBO(bo);
        approvalItemsVO.setApprovalItems(pos);

        return new ResultVO(1000, approvalItemsVO);
    }

    @Override
    public ResultVO confirmCert(HttpSession session, Map<String, Object> reqData) {
        //首先获取审批人的用户id
        Long userId = (Long) session.getAttribute("userId");
        //更新认证审批表
        CertApprovalPO certApprovalPO = new CertApprovalPO();



        return new ResultVO(1000);
    }
}
