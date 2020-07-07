package com.talentcard.web.service;

import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;

import javax.swing.text.StyledEditorKit;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-07 11:34
 * @description
 */
public interface IVerifyTalentPropertyService {
    /**
     * 新增校验
     * @param activcateBO
     * @param educationDTO
     * @return
     */
    Integer verifyEducation(ActivcateBO activcateBO, EducationDTO educationDTO);

    Integer verifyQuality(ActivcateBO activcateBO, ProfQualityDTO profQualityDTO);

    Integer verifyTitle(ActivcateBO activcateBO, ProfTitleDTO profTitleDTO);

    Integer verifyHonour(ActivcateBO activcateBO, TalentHonourDTO talentHonourDTO);

    /**
     * 编辑校验
     * @param activcateBO
     * @param educationDTO
     * @return
     */
    Integer editVerifyEducation(ActivcateBO activcateBO, EducationDTO educationDTO);

    Integer editVerifyQuality(ActivcateBO activcateBO, ProfQualityDTO profQualityDTO);

    Integer editVerifyTitle(ActivcateBO activcateBO, ProfTitleDTO profTitleDTO);

    Integer editVerifyHonour(ActivcateBO activcateBO, TalentHonourDTO talentHonourDTO);
}
