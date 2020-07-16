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
                if (educationPO.getEducPicture2() != null && !educationPO.getEducPicture2().equals("")) {
                    educationPO.setEducPicture2(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture2());
                }
                if (educationPO.getEducPicture3() != null && !educationPO.getEducPicture3().equals("")) {
                    educationPO.setEducPicture3(FilePathConfig.getStaticPublicBasePath() + educationPO.getEducPicture3());
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
                if (profTitlePO.getPicture2() != null && !profTitlePO.getPicture2().equals("")) {
                    profTitlePO.setPicture2(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture2());
                }
                if (profTitlePO.getPicture3() != null && !profTitlePO.getPicture3().equals("")) {
                    profTitlePO.setPicture3(FilePathConfig.getStaticPublicBasePath() + profTitlePO.getPicture3());
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
                if (profQualityPO.getPicture2() != null && !profQualityPO.getPicture2().equals("")) {
                    profQualityPO.setPicture2(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture2());
                }
                if (profQualityPO.getPicture3() != null && !profQualityPO.getPicture3().equals("")) {
                    profQualityPO.setPicture3(FilePathConfig.getStaticPublicBasePath() + profQualityPO.getPicture3());
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
                if (talentHonourPO.getHonourPicture2() != null && !talentHonourPO.getHonourPicture2().equals("")) {
                    talentHonourPO.setHonourPicture2(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture2());
                }
                if (talentHonourPO.getHonourPicture3() != null && !talentHonourPO.getHonourPicture3().equals("")) {
                    talentHonourPO.setHonourPicture3(FilePathConfig.getStaticPublicBasePath() + talentHonourPO.getHonourPicture3());
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
                        String picture2 = insertCertificationBO.getInsertEducationPO().getEducPicture2();
                        if (!StringUtils.isEmpty(picture2)) {
                            insertCertificationBO.getInsertEducationPO().
                                    setEducPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
                        }
                        String picture3 = insertCertificationBO.getInsertEducationPO().getEducPicture3();
                        if (!StringUtils.isEmpty(picture3)) {
                            insertCertificationBO.getInsertEducationPO().
                                    setEducPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
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
                        String picture2 = insertCertificationBO.getInsertTitlePO().getPicture2();
                        if (!StringUtils.isEmpty(picture2)) {
                            insertCertificationBO.getInsertTitlePO().
                                    setPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
                        }
                        String picture3 = insertCertificationBO.getInsertTitlePO().getPicture3();
                        if (!StringUtils.isEmpty(picture3)) {
                            insertCertificationBO.getInsertTitlePO().
                                    setPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
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
                        String picture2 = insertCertificationBO.getInsertQualityPO().getPicture2();
                        if (!StringUtils.isEmpty(picture2)) {
                            insertCertificationBO.getInsertQualityPO().
                                    setPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
                        }
                        String picture3 = insertCertificationBO.getInsertQualityPO().getPicture3();
                        if (!StringUtils.isEmpty(picture3)) {
                            insertCertificationBO.getInsertQualityPO().
                                    setPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
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
                        String picture2 = insertCertificationBO.getInsertHonourPO().getHonourPicture2();
                        if (!StringUtils.isEmpty(picture2)) {
                            insertCertificationBO.getInsertHonourPO().
                                    setHonourPicture2(FilePathConfig.getStaticPublicBasePath() + picture2);
                        }
                        String picture3 = insertCertificationBO.getInsertHonourPO().getHonourPicture3();
                        if (!StringUtils.isEmpty(picture3)) {
                            insertCertificationBO.getInsertHonourPO().
                                    setHonourPicture3(FilePathConfig.getStaticPublicBasePath() + picture3);
                        }
                    }

                }
            }
        }

        talentVO.setInsertCertificationBOList(insertCertificationBOList);
        return talentVO;
    }
}
