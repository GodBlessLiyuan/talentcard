package com.talentcard.web.vo;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.pojo.CertApprovalPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:05 2020/4/15
 * @version: 1.0.0
 * @description:
 */
@Data
public class ApprovalItemsVO {

    ApprovalBO approvalBO;
    /**
     * 审批记录清单items
     */
    private List<CertApprovalPO> approvalItems;
}
