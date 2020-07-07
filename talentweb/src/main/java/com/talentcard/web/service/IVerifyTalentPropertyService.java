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
    Integer verifyEducation(ActivcateBO activcateBO, EducationDTO educationDTO, Byte type);

    Integer verifyQuality(ActivcateBO activcateBO, ProfQualityDTO profQualityDTO, Byte type);

    Integer verifyTitle(ActivcateBO activcateBO, ProfTitleDTO profTitleDTO, Byte type);

    Integer verifyHonour(ActivcateBO activcateBO, TalentHonourDTO talentHonourDTO, Byte type);
}
