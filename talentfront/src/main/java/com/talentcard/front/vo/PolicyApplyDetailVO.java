package com.talentcard.front.vo;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.pojo.AnnexPO;
import com.talentcard.common.pojo.PolicyApprovalPO;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 19:17
 * @description: 我的申请 - 详情
 * @version: 1.0
 */
@Data
public class PolicyApplyDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 申请状态
     */
    private String status;
    /**
     * 持卡人
     */
    private String name;
    /**
     * 银行卡号
     */
    private String card;
    /**
     * 开户行号
     */
    private String bank;
    /**
     * 附件名称
     */
    private List<String> annexes;

    /**
     * 原因
     */
    private String reason;

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static PolicyApplyDetailVO convert(PolicyApplyBO bo) {
        PolicyApplyDetailVO vo = new PolicyApplyDetailVO();

        vo.setApplyDate(DateUtil.date2Str(bo.getCreateTime(), DateUtil.YMD));
        vo.setStatus(bo.getStatus() == 1 ? "审核通过" : bo.getStatus() == 2 ? "审核中" : bo.getStatus() == 3 ? "审核驳回" : "无");
        vo.setName(bo.getTalentName());
        vo.setCard(bo.getBankNum());
        vo.setBank(bo.getBankName());

        List<String> annexes = new ArrayList<>();
        for (AnnexPO po : bo.getAnnexes()) {
            annexes.add(po.getName());
        }
        vo.setAnnexes(annexes);

        for (PolicyApprovalPO po : bo.getApproval()) {
            if (null != po.getOpinion()) {
                vo.setReason(po.getOpinion());
                break;
            }
        }

        return vo;
    }
}
