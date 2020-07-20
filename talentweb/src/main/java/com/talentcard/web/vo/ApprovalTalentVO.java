package com.talentcard.web.vo;

import com.talentcard.common.bo.CertApprovalBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.TalentHonourMapper;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentHonourPO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    private Byte political;
    private Date createTime;
    private Date completeCertificationTime;
    private String workLocation;
    private Byte workLocationType;
    private Byte cardType;
    private String passport;
    private String driverCard;
    private Integer talentSource;
    /**
     * 三个表信息
     */
    private List<EducationPO> educationPOList;
    private List<ProfTitlePO> profTitlePOList;
    private List<ProfQualityPO> profQualityPOList;
    private List<TalentHonourPO> talentHonourPOList;

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
    public static ApprovalTalentVO convert(TalentBO talentBO, List<CertApprovalBO> certApprovalBOList) {
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
        approvalTalentVO.setCreateTime(talentBO.getCreateTime());
        approvalTalentVO.setCardType(talentBO.getCardType());
        approvalTalentVO.setWorkLocationType(talentBO.getWorkLocationType());
        approvalTalentVO.setWorkLocation(talentBO.getWorkLocation());
        approvalTalentVO.setCardType(talentBO.getCardType());
        approvalTalentVO.setDriverCard(talentBO.getDriverCard());
        approvalTalentVO.setPassport(talentBO.getPassport());
        approvalTalentVO.setTalentSource(talentBO.getTalentSource());

//        //学历文件
//        if (talentBO.getEducationPOList() != null) {
//            for (EducationPO educationPO : talentBO.getEducationPOList()) {
//                if (!StringUtils.isEmpty(educationPO.getEducPicture())) {
//                    educationPO.setEducPicture(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture());
//                }
//                if (!StringUtils.isEmpty(educationPO.getEducPicture2())) {
//                    educationPO.setEducPicture2(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture2());
//                }
//                if (!StringUtils.isEmpty(educationPO.getEducPicture3())) {
//                    educationPO.setEducPicture3(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture3());
//                }
//            }
//        }
//        //职称文件
//        if (talentBO.getProfTitlePOList() != null) {
//            for (ProfTitlePO profTitlePO : talentBO.getProfTitlePOList()) {
//                if (!StringUtils.isEmpty(profTitlePO.getPicture())) {
//                    profTitlePO.setPicture(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture());
//                }
//                if (!StringUtils.isEmpty(profTitlePO.getPicture2())) {
//                    profTitlePO.setPicture2(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture2());
//                }
//                if (!StringUtils.isEmpty(profTitlePO.getPicture3())) {
//                    profTitlePO.setPicture3(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture3());
//                }
//            }
//        }
//        //职业资格文件
//        if (talentBO.getProfQualityPOList() != null) {
//            for (ProfQualityPO profQualityPO : talentBO.getProfQualityPOList()) {
//                if (!StringUtils.isEmpty(profQualityPO.getPicture())) {
//                    profQualityPO.setPicture(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture());
//                }
//                if (!StringUtils.isEmpty(profQualityPO.getPicture2())) {
//                    profQualityPO.setPicture2(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture2());
//                }
//                if (!StringUtils.isEmpty(profQualityPO.getPicture3())) {
//                    profQualityPO.setPicture3(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture3());
//                }
//            }
//        }
//        //人才荣誉
//        if (talentBO.getTalentHonourPOList() != null) {
//            for (TalentHonourPO talentHonourPO : talentBO.getTalentHonourPOList()) {
//                if (!StringUtils.isEmpty(talentHonourPO.getHonourPicture())) {
//                    talentHonourPO.setHonourPicture(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture());
//                }
//                if (!StringUtils.isEmpty(talentHonourPO.getHonourPicture2())) {
//                    talentHonourPO.setHonourPicture2(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture2());
//                }
//                if (!StringUtils.isEmpty(talentHonourPO.getHonourPicture3())) {
//                    talentHonourPO.setHonourPicture3(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture3());
//                }
//            }
//        }
        approvalTalentVO.setEducationPOList(talentBO.getEducationPOList());
        approvalTalentVO.setProfTitlePOList(talentBO.getProfTitlePOList());
        approvalTalentVO.setProfQualityPOList(talentBO.getProfQualityPOList());
        approvalTalentVO.setTalentHonourPOList(talentBO.getTalentHonourPOList());

        approvalTalentVO.setCertApprovalBOList(certApprovalBOList);

        for (CertApprovalBO certApprovalBO : certApprovalBOList) {
            //认证完成时间 审批通过/驳回的时间
            if (certApprovalBO.getCertId().equals(talentBO.getCertId())) {
                approvalTalentVO.setCompleteCertificationTime(certApprovalBO.getUpdateTime());
            }
        }
        return approvalTalentVO;
    }
}
