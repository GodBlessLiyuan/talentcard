package com.talentcard.web.vo;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.bo.CertApprovalBO;
import com.talentcard.common.config.FilePathConfig;
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
    private List<CertApprovalBO> approvalItems;

    public static ApprovalItemsVO convert(ApprovalItemsVO vo) {
        if (vo.getApprovalBO().getPtPic()!=null && !("").equals(vo.getApprovalBO().getPtPic())) {
            vo.getApprovalBO().setPtPic(FilePathConfig.getStaticPublicBasePath()+vo.getApprovalBO().getPtPic());
        }
        if (vo.getApprovalBO().getEducPic()!=null && !("").equals(vo.getApprovalBO().getEducPic())) {
            vo.getApprovalBO().setEducPic(FilePathConfig.getStaticPublicBasePath()+vo.getApprovalBO().getEducPic());
        }
        if (vo.getApprovalBO().getPqPic()!=null && !("").equals(vo.getApprovalBO().getPqPic())) {
            vo.getApprovalBO().setPqPic(FilePathConfig.getStaticPublicBasePath()+vo.getApprovalBO().getPqPic());
        }
        return vo;
    }
}
