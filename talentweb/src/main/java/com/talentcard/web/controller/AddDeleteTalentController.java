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

import javax.servlet.http.HttpServletRequest;
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
    public ResultVO addEducation(HttpServletRequest request, @RequestBody EducationDTO educationDTO) {
        return iAddDeleteTalentService.addEducation(request.getSession(), educationDTO);
    }

    /**
     * 新增职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(HttpServletRequest request, @RequestBody ProfQualityDTO profQualityDTO) {
        return iAddDeleteTalentService.addProfQuality(request.getSession(), profQualityDTO);
    }

    /**
     * 新增职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(HttpServletRequest request, @RequestBody ProfTitleDTO profTitleDTO) {
        return iAddDeleteTalentService.addProfTitle(request.getSession(), profTitleDTO);
    }

    /**
     * 新增人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(HttpServletRequest request, @RequestBody TalentHonourDTO talentHonourDTO) {
        return iAddDeleteTalentService.addTalentHonour(request.getSession(), talentHonourDTO);
    }

    /**
     * 删除学历
     *
     * @param openId
     * @param educId
     * @return
     */
    @RequestMapping("deleteEducation")
    public ResultVO deleteEducation(HttpServletRequest request,
                                    @RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "educId") Long educId,
                                    @RequestParam(value = "opinion", required = false, defaultValue = "") String opinion) {
        return iAddDeleteTalentService.deleteEducation(request.getSession(), openId, educId, opinion);
    }

    /**
     * 删除职业资格
     *
     * @param openId
     * @param pqId
     * @return
     */
    @RequestMapping("deleteProfQuality")
    public ResultVO deleteProfQuality(HttpServletRequest request,
                                      @RequestParam(value = "openId") String openId,
                                      @RequestParam(value = "pqId") Long pqId,
                                      @RequestParam(value = "opinion", required = false, defaultValue = "") String opinion) {
        return iAddDeleteTalentService.deleteProfQuality(request.getSession(), openId, pqId, opinion);
    }

    /**
     * 删除职称
     *
     * @param openId
     * @param ptId
     * @return
     */
    @RequestMapping("deleteProfTitle")
    public ResultVO deleteProfTitle(HttpServletRequest request,
                                    @RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "ptId") Long ptId,
                                    @RequestParam(value = "opinion", required = false, defaultValue = "") String opinion) {
        return iAddDeleteTalentService.deleteProfTitle(request.getSession(), openId, ptId, opinion);
    }

    /**
     * 删除人才荣誉
     *
     * @param openId
     * @param thId
     * @return
     */
    @RequestMapping("deleteTalentHonour")
    public ResultVO deleteTalentHonour(HttpServletRequest request,
                                       @RequestParam(value = "openId") String openId,
                                       @RequestParam(value = "thId") Long thId,
                                       @RequestParam(value = "opinion", required = false, defaultValue = "") String opinion) {
        return iAddDeleteTalentService.deleteTalentHonour(request.getSession(), openId, thId, opinion);
    }
}
