package com.talentcard.web.service;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:07
 * @description
 */
public interface IAddDeleteTalentService {
    /**
     * 新增
     *
     * @param educationDTO
     * @return
     */
    ResultVO addEducation(EducationDTO educationDTO);


    /**
     * 新增
     */
    ResultVO addProfQuality(ProfQualityDTO profQualityDTO);


    /**
     * 新增
     */
    ResultVO addProfTitle(ProfTitleDTO profTitleDTO);

    /**
     * 新增
     */
    ResultVO addTalentHonour(TalentHonourDTO talentHonourDTO);

    /**
     * 删除
     *
     * @param educId
     * @return
     */
    ResultVO deleteEducation(Long educId);


    /**
     * 删除
     * @param pqId
     * @return
     */
    ResultVO deleteProfQuality(Long pqId);


    /**
     * 删除
     * @param ptId
     * @return
     */
    ResultVO deleteProfTitle(Long ptId);

    /**
     * 删除
     * @param thId
     * @return
     */
    ResultVO deleteTalentHonour(Long thId);
}
