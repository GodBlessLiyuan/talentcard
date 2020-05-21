package com.talentcard.web.vo;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.pojo.AnnexPO;
import com.talentcard.common.pojo.PolicyApprovalPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.config.FilePathConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/15 15:02
 * @description: 政策权益申请-详情
 * @version: 1.0
 */
@Data
public class PolicyApplyDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 政策申请ID
     */
    private Long paid;
    /**
     * 申请人
     */
    private String tname;
    /**
     * 申请时间
     */
    private String ctime;
    /**
     * 当前状态
     */
    private String status;
    /**
     * 政策权益名称
     */
    private String pname;
    /**
     * 政策权益编号
     */
    private String pnum;
    /**
     * 银行卡信息
     */
    private BankVO bank;
    /**
     * 证明附件
     */
    private List<AnnexVO> annexes;
    /**
     * 审批记录
     */
    private List<PolicyApprovalVO> approval;

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static PolicyApplyDetailVO convert(PolicyApplyBO bo) {
        PolicyApplyDetailVO vo = new PolicyApplyDetailVO();
        vo.setPaid(bo.getPaId());
        vo.setTname(bo.getTalentName());
        vo.setCtime(DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD_HMS));
        vo.setStatus(bo.getStatus() == 1 ? "已同意" : bo.getStatus() == 2 ? "已驳回" : "待审批");
        vo.setPname(bo.getPolicyName());
        vo.setPnum(bo.getNum());

        if (null != bo.getBankNum()) {
            BankVO bankVO = new BankVO();
            bankVO.setUname(bo.getTalentName());
            bankVO.setBname(bo.getBankName());
            bankVO.setBnum(bo.getBankNum());
            vo.setBank(bankVO);
        }

        if (null != bo.getAnnexes() && bo.getAnnexes().size() > 0) {
            List<AnnexVO> annexVOs = new LinkedList<>();
            for (AnnexPO po : bo.getAnnexes()) {
                AnnexVO annexVO = new AnnexVO();
                annexVO.setName(po.getName());
                annexVO.setUrl(FilePathConfig.getStaticPublicBasePath() + po.getLocation());
                annexVOs.add(annexVO);
            }
            vo.setAnnexes(annexVOs);
        }

        if (null != bo.getApproval() && bo.getApproval().size() > 0) {
            List<PolicyApprovalVO> approvalVOs = new LinkedList<>();
            for (PolicyApprovalPO po : bo.getApproval()) {
                PolicyApprovalVO approvalVO = new PolicyApprovalVO();
                approvalVO.setCtime(DateUtil.date2Str(po.getCreateTime(), DateUtil.YMD_HMS));
                approvalVO.setType(po.getType() == 1 ? "提交申请" : "审批");
                approvalVO.setUname(null == po.getUsername() || "".equals(po.getUsername()) ? "无" : po.getUsername());
                approvalVO.setResult(null == po.getResult() ? "无" : po.getResult() == 1 ? "通过" : "驳回");
                approvalVO.setOpinion(null == po.getOpinion() || "".equals(po.getOpinion()) ? "无" : po.getOpinion());
                approvalVOs.add(approvalVO);
            }
            vo.setApproval(approvalVOs);
        }

        return vo;
    }
}
