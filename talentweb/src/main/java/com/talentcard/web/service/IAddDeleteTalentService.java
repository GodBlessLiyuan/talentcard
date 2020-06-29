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
     * @param openId
     * @param educId
     * @return
     */
    ResultVO deleteEducation(String openId, Long educId);


    /**
     * 删除
     * @param openId
     * @param pqId
     * @return
     */
    ResultVO deleteProfQuality(String openId, Long pqId);

    /**
     * 删除
     * @param openId
     * @param ptId
     * @return
     */
    ResultVO deleteProfTitle(String openId, Long ptId);

    /**
     * 删除
     * @param openId
     * @param thId
     * @return
     */
    ResultVO deleteTalentHonour(String openId, Long thId);
}
