package com.talentcard.web.vo;

import com.talentcard.common.bo.CertApprovalBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.web.utils.WebParameterUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-08 14:05
 * @description
 */
@Data
@Component
public class ApprovalTalentVO {
    private Long talentId;
    private String openId;
    private String name;
    private Byte sex;
    private String idCard;
    private String workUnit;
    private Integer industry;
    private Integer industrySecond;
    private String phone;
    private String category;
    private Long cardId;
    private Long certId;
    private String political;
    /**
     * 三个表信息
     */
    private List<EducationPO> educationPOList;
    private List<ProfTitlePO> profTitlePOList;
    private List<ProfQualityPO> profQualityPOList;

    /**
     * 审批记录清单items
     */
    private List<CertApprovalBO> certApprovalBOList;


    /**
     * Bo转VO
     *
     * @param talentBO
     * @return
     */
    public static ApprovalTalentVO convert(TalentBO talentBO,  List<CertApprovalBO> certApprovalBOList) {
        ApprovalTalentVO approvalTalentVO = new ApprovalTalentVO();
        approvalTalentVO.setTalentId(talentBO.getTalentId());
        approvalTalentVO.setOpenId(talentBO.getOpenId());
        approvalTalentVO.setName(talentBO.getName());
        approvalTalentVO.setIdCard(talentBO.getIdCard());
        approvalTalentVO.setWorkUnit(talentBO.getWorkUnit());
        approvalTalentVO.setIndustry(talentBO.getIndustry());
        approvalTalentVO.setIndustrySecond(talentBO.getIndustrySecond());
        approvalTalentVO.setPhone(talentBO.getPhone());
        approvalTalentVO.setCertId(talentBO.getCertId());
        approvalTalentVO.setPolitical(talentBO.getPolitical());
        approvalTalentVO.setSex(talentBO.getSex());

        //学历文件
        if (talentBO.getEducationPOList() != null) {
            for (EducationPO educationPO : talentBO.getEducationPOList()) {
                if (educationPO.getEducPicture() != null && !educationPO.getEducPicture().equals("")) {
                    educationPO.setEducPicture(WebParameterUtil.getPublicPath() + educationPO.getEducPicture());
                }
            }
        }
        //职称文件
        if (talentBO.getProfTitlePOList() != null) {
            for (ProfTitlePO profTitlePO : talentBO.getProfTitlePOList()) {
                if (profTitlePO.getPicture() != null && !profTitlePO.getPicture().equals("")) {
                    profTitlePO.setPicture(WebParameterUtil.getPublicPath() + profTitlePO.getPicture());
                }
            }
        }
        //职业资格文件
        if (talentBO.getProfQualityPOList() != null) {
            for (ProfQualityPO profQualityPO : talentBO.getProfQualityPOList()) {
                if (profQualityPO.getPicture() != null && !profQualityPO.getPicture().equals("")) {
                    profQualityPO.setPicture(WebParameterUtil.getPublicPath() + profQualityPO.getPicture());
                }
            }
        }
        approvalTalentVO.setEducationPOList(talentBO.getEducationPOList());
        approvalTalentVO.setProfTitlePOList(talentBO.getProfTitlePOList());
        approvalTalentVO.setProfQualityPOList(talentBO.getProfQualityPOList());

        approvalTalentVO.setCertApprovalBOList(certApprovalBOList);
        return approvalTalentVO;
    }
}
