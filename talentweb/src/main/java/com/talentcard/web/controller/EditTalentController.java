package com.talentcard.web.controller;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EditTalentPolicyDTO;
import com.talentcard.web.service.IEditTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-28 14:12
 * @description
 */
@RequestMapping("editTalent")
@RestController
public class EditTalentController {
    @Autowired
    IEditTalentService iEditTalentService;

    @RequestMapping("editBasicInfo")
    public ResultVO editBasicInfo(HttpSession httpSession, @RequestBody BasicInfoDTO basicInfoDTO) {
        return iEditTalentService.editBasicInfo(httpSession, basicInfoDTO);
    }

    /**
     * 学历
     *
     * @param educationDTO
     * @return
     */
    @RequestMapping("editEducation")
    public ResultVO editEducation(HttpSession httpSession, @RequestBody EducationDTO educationDTO) {
        return iEditTalentService.editEducation(httpSession, educationDTO);
    }

    /**
     * 职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("editProfQuality")
    public ResultVO editProfQuality(HttpSession httpSession, @RequestBody ProfQualityDTO profQualityDTO) {
        return iEditTalentService.editProfQuality(httpSession, profQualityDTO);
    }

    /**
     * 职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("editProfTitle")
    public ResultVO editProfTitle(HttpSession httpSession, @RequestBody ProfTitleDTO profTitleDTO) {
        return iEditTalentService.editProfTitle(httpSession, profTitleDTO);
    }

    /**
     * 人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("editTalentHonour")
    public ResultVO editTalentHonour(HttpSession httpSession, @RequestBody TalentHonourDTO talentHonourDTO) {
        return iEditTalentService.editTalentHonour(httpSession, talentHonourDTO);
    }

    /**
     * 人才类别
     *
     * @param openId
     * @param talentCategory
     * @return
     */
    @RequestMapping("editTalentCategory")
    public ResultVO editTalentCategory(HttpSession httpSession,
                                       @RequestParam(value = "openId") String openId,
                                       @RequestParam(value = "talentCategory") String talentCategory,
                                       @RequestParam(value = "opinion") String opinion) {
        return iEditTalentService.editTalentCategory(httpSession, openId, talentCategory, opinion);
    }

    /**
     * 根据属性查找政策权益
     *
     * @param editTalentPolicyDTO
     * @return
     */
    @RequestMapping("findPolicy")
    public ResultVO findPolicy(@RequestBody EditTalentPolicyDTO editTalentPolicyDTO) {
        return iEditTalentService.findPolicy(editTalentPolicyDTO);
    }

    /**
     * @param openId
     * @return
     */
    @RequestMapping("findTalentCertificationDetail")
    public ResultVO findTalentCertificationDetail(@RequestParam(value = "openId") String openId) {
        return iEditTalentService.findTalentCertificationDetail(openId);
    }

    /**
     * 更换卡
     *
     * @param talentId
     * @param cardId
     * @return
     */
    @RequestMapping("changeCard")
    public ResultVO changeCard(@RequestParam(value = "talentId") Long talentId,
                               @RequestParam(value = "cardId") Long cardId,
                               @RequestParam(value = "opinion") String opinion) {
        return iEditTalentService.changeCard(talentId, cardId, opinion);
    }

    /**
     * 能换的卡查询
     *
     * @param talentId
     * @return
     */
    @RequestMapping("findEnableChangeCard")
    public ResultVO findEnableChangeCard(@RequestParam(value = "talentId") Long talentId) {
        return iEditTalentService.findEnableChangeCard(talentId);
    }
}
