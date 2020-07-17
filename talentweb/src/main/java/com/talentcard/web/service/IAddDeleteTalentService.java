package com.talentcard.web.service;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;

import javax.servlet.http.HttpSession;

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
    ResultVO addEducation(HttpSession httpSession, EducationDTO educationDTO);


    /**
     * 新增
     */
    ResultVO addProfQuality(HttpSession httpSession, ProfQualityDTO profQualityDTO);


    /**
     * 新增
     */
    ResultVO addProfTitle(HttpSession httpSession, ProfTitleDTO profTitleDTO);

    /**
     * 新增
     */
    ResultVO addTalentHonour(HttpSession httpSession, TalentHonourDTO talentHonourDTO);

    /**
     * 删除
     *
     * @param openId
     * @param educId
     * @return
     */
    ResultVO deleteEducation(HttpSession httpSession, String openId, Long educId, String opinion);


    /**
     * 删除
     *
     * @param openId
     * @param pqId
     * @return
     */
    ResultVO deleteProfQuality(HttpSession httpSession, String openId, Long pqId, String opinion);

    /**
     * 删除
     *
     * @param openId
     * @param ptId
     * @return
     */
    ResultVO deleteProfTitle(HttpSession httpSession, String openId, Long ptId, String opinion);

    /**
     * 删除
     *
     * @param openId
     * @param thId
     * @return
     */
    ResultVO deleteTalentHonour(HttpSession httpSession, String openId, Long thId, String opinion);
}
