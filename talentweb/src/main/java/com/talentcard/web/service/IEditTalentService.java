package com.talentcard.web.service;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:07
 * @description
 */
public interface IEditTalentService {
    /**
     * 编辑学历
     * @param basicInfoDTO
     * @return
     */
    ResultVO editBasicInfo(BasicInfoDTO basicInfoDTO);

    /**
     * 新增/编辑学历
     *
     * @param educationDTO
     * @return
     */
    ResultVO editEducation(EducationDTO educationDTO);


    /**
     * 新增/编辑职称
     */
    ResultVO editProfQuality(ProfQualityDTO profQualityDTO);


    /**
     * 新增/编辑职业资格
     */
    ResultVO editProfTitle(ProfTitleDTO profTitleDTO);

    /**
     * 新增/编辑人才荣誉
     */
    ResultVO editTalentHonour(TalentHonourDTO talentHonourDTO);

    /**
     * 新增/编辑人才荣誉
     */
    ResultVO editTalentCategory(String openId, String talentCategory);
}
