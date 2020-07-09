package com.talentcard.web.controller;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IAddDeleteTalentService;
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
@RequestMapping("addDeleteTalent")
@RestController
public class AddDeleteTalentController {
    @Autowired
    IAddDeleteTalentService iAddDeleteTalentService;

    /**
     * 新增学历
     *
     * @param educationDTO
     * @return
     */
    @RequestMapping("addEducation")
    public ResultVO addEducation(HttpSession httpSession, @RequestBody EducationDTO educationDTO) {
        return iAddDeleteTalentService.addEducation(httpSession, educationDTO);
    }

    /**
     * 新增职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(HttpSession httpSession, @RequestBody ProfQualityDTO profQualityDTO) {
        return iAddDeleteTalentService.addProfQuality(httpSession, profQualityDTO);
    }

    /**
     * 新增职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(HttpSession httpSession, @RequestBody ProfTitleDTO profTitleDTO) {
        return iAddDeleteTalentService.addProfTitle(httpSession, profTitleDTO);
    }

    /**
     * 新增人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(HttpSession httpSession, @RequestBody TalentHonourDTO talentHonourDTO) {
        return iAddDeleteTalentService.addTalentHonour(httpSession, talentHonourDTO);
    }

    /**
     * 删除学历
     *
     * @param openId
     * @param educId
     * @return
     */
    @RequestMapping("deleteEducation")
    public ResultVO deleteEducation(HttpSession httpSession,
                                    @RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "educId") Long educId) {
        return iAddDeleteTalentService.deleteEducation(httpSession, openId, educId);
    }

    /**
     * 删除职业资格
     *
     * @param openId
     * @param pqId
     * @return
     */
    @RequestMapping("deleteProfQuality")
    public ResultVO deleteProfQuality(HttpSession httpSession,
                                      @RequestParam(value = "openId") String openId,
                                      @RequestParam(value = "pqId") Long pqId) {
        return iAddDeleteTalentService.deleteProfQuality(httpSession, openId, pqId);
    }

    /**
     * 删除职称
     *
     * @param openId
     * @param ptId
     * @return
     */
    @RequestMapping("deleteProfTitle")
    public ResultVO deleteProfTitle(HttpSession httpSession,
                                    @RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "ptId") Long ptId) {
        return iAddDeleteTalentService.deleteProfTitle(httpSession, openId, ptId);
    }

    /**
     * 删除人才荣誉
     *
     * @param openId
     * @param thId
     * @return
     */
    @RequestMapping("deleteTalentHonour")
    public ResultVO deleteTalentHonour(HttpSession httpSession,
                                       @RequestParam(value = "openId") String openId,
                                       @RequestParam(value = "thId") Long thId) {
        return iAddDeleteTalentService.deleteTalentHonour(httpSession, openId, thId);
    }
}
