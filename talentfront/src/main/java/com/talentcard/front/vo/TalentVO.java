package com.talentcard.front.vo;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ChenXU
 * @date: Created in 2020/4/23 13:00
 * @description: 人才信息带四表详情
 * @version: 1.0
 */
@Data
@Component
public class TalentVO {
    private Long talentId;
    private String openId;
    private String name;
    private Byte sex;
    private String idCard;
    private String passport;
    private String workUnit;
    private Integer industry;
    private Integer industrySecond;
    private String phone;
    private Byte talentStatus;
    private String talentCategory;
    private Long certId;
    private String political;
    private Byte certificationStatus;
    private Byte currentType;
    List<EducationPO> educationPOList;
    List<ProfTitlePO> profTitlePOList;
    List<ProfQualityPO> profQualityPOList;
    private static String publicPath;

    /**
     * Bo转VO
     *
     * @param talentBO
     * @return
     */
    public static TalentVO convert(TalentBO talentBO) {
        TalentVO talentVO = new TalentVO();
        talentVO.setTalentId(talentBO.getTalentId());
        talentVO.setOpenId(talentBO.getOpenId());
        talentVO.setName(talentBO.getName());
        talentVO.setIdCard(talentBO.getIdCard());
//        talentVO.setPassport(talentBO.getPassport());
        talentVO.setWorkUnit(talentBO.getWorkUnit());
        talentVO.setIndustry(talentBO.getIndustry());
        talentVO.setIndustrySecond(talentBO.getIndustrySecond());
        talentVO.setPhone(talentBO.getPhone());
        talentVO.setTalentStatus(talentBO.getStatus());
        talentVO.setTalentCategory(talentBO.getCategory());
        talentVO.setCertId(talentBO.getCertId());
        talentVO.setPolitical(talentBO.getPolitical());
        talentVO.setCertificationStatus(talentBO.getCertificationStatus());
        talentVO.setCurrentType(talentBO.getCurrentType());
        talentVO.setEducationPOList(talentBO.getEducationPOList());
        talentVO.setProfTitlePOList(talentBO.getProfTitlePOList());
        talentVO.setProfQualityPOList(talentBO.getProfQualityPOList());
        //学历文件
        if (talentBO.getEducationPOList() != null) {
            for (EducationPO educationPO : talentBO.getEducationPOList()) {
                if (educationPO.getEducPicture() != null) {
                    educationPO.setEducPicture(publicPath + educationPO.getEducPicture());
                }
            }
        }
        //职称文件
        if (talentBO.getProfTitlePOList() != null) {
            for (ProfTitlePO profTitlePO : talentBO.getProfTitlePOList()) {
                if (profTitlePO.getPicture() != null) {
                    profTitlePO.setPicture(publicPath + profTitlePO.getPicture());
                }
            }
        }
        //职业资格文件
        if (talentBO.getProfQualityPOList() != null) {
            for (ProfQualityPO profQualityPO : talentBO.getProfQualityPOList()) {
                if (profQualityPO.getPicture() != null) {
                    profQualityPO.setPicture(publicPath + profQualityPO.getPicture());
                }
            }
        }
        return talentVO;
    }

    /**
     * BOS转VOS
     *
     * @param talentBOList
     * @return
     */
    public static List<TalentVO> convert(List<TalentBO> talentBOList) {
        List<TalentVO> talentVOList = new ArrayList<>();
        for (TalentBO talentBO : talentBOList) {
            talentVOList.add(TalentVO.convert(talentBO));
        }


        return talentVOList;
    }

    @Value("${file.publicPath}")
    private void setPublicPath(String publicPath) {
        TalentVO.publicPath = publicPath;
    }
}