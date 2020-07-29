package com.talentcard.web.service;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EditTalentPolicyDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:07
 * @description
 */
public interface IEditTalentService {
    /**
     * 编辑学历
     *
     * @param basicInfoDTO
     * @return
     */
    ResultVO editBasicInfo(HttpSession httpSession, BasicInfoDTO basicInfoDTO);

    /**
     * 新增/编辑学历
     *
     * @param educationDTO
     * @return
     */
    ResultVO editEducation(HttpSession httpSession, EducationDTO educationDTO);


    /**
     * 新增/编辑职称
     */
    ResultVO editProfQuality(HttpSession httpSession, ProfQualityDTO profQualityDTO);


    /**
     * 新增/编辑职业资格
     */
    ResultVO editProfTitle(HttpSession httpSession, ProfTitleDTO profTitleDTO);

    /**
     * 新增/编辑人才荣誉
     */
    ResultVO editTalentHonour(HttpSession httpSession, TalentHonourDTO talentHonourDTO);

    /**
     * 新增/编辑人才荣誉
     */
    ResultVO editTalentCategory(HttpSession httpSession, String openId, String talentCategory, String opinion);

    /**
     * 政策查询
     * @param editTalentPolicyDTO
     * @return
     */
    ResultVO findPolicy(EditTalentPolicyDTO editTalentPolicyDTO);

    /**
     * 认证人才详情
     * @param openId
     * @return
     */
    ResultVO findTalentCertificationDetail(String openId);

    /**
     * 换卡
     * @param talentId
     * @param cardId
     * @return
     */
    ResultVO changeCard(HttpSession session,Long talentId, Long cardId, String opinion);

    /**
     * 可以更换的卡查询
     * @param talentId
     * @return
     */
    ResultVO findEnableChangeCard(Long talentId);
}
