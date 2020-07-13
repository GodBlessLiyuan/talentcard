package com.talentcard.front.vo;

import com.talentcard.common.bo.InsertCertificationBO;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.EducationPO;
import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import com.talentcard.common.pojo.TalentHonourPO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
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
    private Byte cardType;
    private String idCard;
    private String passport;
    private String driverCard;
    private String workUnit;
    private Integer industry;
    private Integer industrySecond;
    private String phone;
    private Byte talentStatus;
    private String talentCategory;
    private Long certId;
    private Byte political;
    private Byte certificationStatus;
    private Byte currentType;
    private Byte workLocationType;
    private String workLocation;
    private Integer TalentSource;
    private List<EducationPO> educationPOList;
    private List<ProfTitlePO> profTitlePOList;
    private List<ProfQualityPO> profQualityPOList;
    private List<TalentHonourPO> talentHonourPOList;

    private List<InsertCertificationBO> insertCertificationBOList;

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
        talentVO.setPassport(talentBO.getPassport());
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
        talentVO.setSex(talentBO.getSex());
        talentVO.setCardType(talentBO.getCardType());
        talentVO.setWorkLocation(talentBO.getWorkLocation());
        talentVO.setWorkLocationType(talentBO.getWorkLocationType());
        talentVO.setDriverCard(talentBO.getDriverCard());
        talentVO.setTalentSource(talentBO.getTalentSource());
        List<EducationPO> resultEducationList = new ArrayList<>();
        List<ProfTitlePO> resultTitleList = new ArrayList<>();
        List<ProfQualityPO> resultQualityList = new ArrayList<>();
        List<TalentHonourPO> resultHonourList = new ArrayList<>();
        //学历文件
        if (talentBO.getEducationPOList() != null) {
            for (EducationPO educationPO : talentBO.getEducationPOList()) {
                if (educationPO.getEducPicture() != null && !educationPO.getEducPicture().equals("")) {
                    educationPO.setEducPicture(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture());
                }
                //不为0，才返回结果
                if (educationPO.getEducation() != null && educationPO.getEducation() != 0) {
                    resultEducationList.add(educationPO);
                }
            }
        }

        //职称文件
        if (talentBO.getProfTitlePOList() != null) {
            for (ProfTitlePO profTitlePO : talentBO.getProfTitlePOList()) {
                if (profTitlePO.getPicture() != null && !profTitlePO.getPicture().equals("")) {
                    profTitlePO.setPicture(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture());
                }
                //不为0，才返回结果
                if (profTitlePO.getCategory() != null && profTitlePO.getCategory() != 0) {
                    resultTitleList.add(profTitlePO);
                }
            }
        }

        //职业资格文件
        if (talentBO.getProfQualityPOList() != null) {
            for (ProfQualityPO profQualityPO : talentBO.getProfQualityPOList()) {
                if (profQualityPO.getPicture() != null && !profQualityPO.getPicture().equals("")) {
                    profQualityPO.setPicture(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture());
                }
                //不为0，才返回结果
                if (profQualityPO.getCategory() != null && profQualityPO.getCategory() != 0) {
                    resultQualityList.add(profQualityPO);
                }
            }
        }
        //人才荣誉
        if (talentBO.getTalentHonourPOList() != null) {
            for (TalentHonourPO talentHonourPO : talentBO.getTalentHonourPOList()) {
                if (talentHonourPO.getHonourPicture() != null && !talentHonourPO.getHonourPicture().equals("")) {
                    talentHonourPO.setHonourPicture(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture());
                }
                //不为0，才返回结果
                if (talentHonourPO.getHonourId() != null && talentHonourPO.getHonourId() != 0) {
                    resultHonourList.add(talentHonourPO);
                }
            }
        }
        talentVO.setEducationPOList(resultEducationList);
        talentVO.setProfTitlePOList(resultTitleList);
        talentVO.setProfQualityPOList(resultQualityList);
        talentVO.setTalentHonourPOList(resultHonourList);
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

    public static TalentVO setInsertCertification(TalentVO talentVO, List<InsertCertificationBO> insertCertificationBOList) {
        if (insertCertificationBOList == null) {
            return talentVO;
        }
        for (InsertCertificationBO insertCertificationBO : insertCertificationBOList) {
            if (insertCertificationBO != null) {
                if (insertCertificationBO.getType() == 1) {
                    //学历
                    if (insertCertificationBO.getInsertEducationPO() != null) {
                        String picture = insertCertificationBO.getInsertEducationPO().getEducPicture();
                        if (!StringUtils.isEmpty(picture)) {
                            insertCertificationBO.getInsertEducationPO().
                                    setEducPicture(FilePathConfig.getStaticPublicBasePath() + picture);
                        }
                    }
                } else if (insertCertificationBO.getType() == 2) {
                    //职称
                    if (insertCertificationBO.getInsertTitlePO() != null) {
                        String picture = insertCertificationBO.getInsertTitlePO().getPicture();
                        if (!StringUtils.isEmpty(picture)) {
                            insertCertificationBO.getInsertTitlePO().
                                    setPicture(FilePathConfig.getStaticPublicBasePath() + picture);
                        }
                    }
                } else if (insertCertificationBO.getType() == 3) {
                    //职业资格
                    if (insertCertificationBO.getInsertQualityPO() != null) {
                        String picture = insertCertificationBO.getInsertQualityPO().getPicture();
                        if (!StringUtils.isEmpty(picture)) {
                            insertCertificationBO.getInsertQualityPO().
                                    setPicture(FilePathConfig.getStaticPublicBasePath() + picture);
                        }
                    }
                } else {
                    if (insertCertificationBO.getInsertHonourPO() != null) {
                        //人才荣誉
                        String picture = insertCertificationBO.getInsertHonourPO().getHonourPicture();
                        if (!StringUtils.isEmpty(picture)) {
                            insertCertificationBO.getInsertHonourPO().
                                    setHonourPicture(FilePathConfig.getStaticPublicBasePath() + picture);
                        }
                    }

                }
            }
        }

        talentVO.setInsertCertificationBOList(insertCertificationBOList);
        return talentVO;
    }
}
