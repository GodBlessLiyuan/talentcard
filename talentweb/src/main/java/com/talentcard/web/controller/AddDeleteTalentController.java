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
    public ResultVO addEducation(@RequestBody EducationDTO educationDTO) {
        return iAddDeleteTalentService.addEducation(educationDTO);
    }

    /**
     * 新增职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(@RequestBody ProfQualityDTO profQualityDTO) {
        return iAddDeleteTalentService.addProfQuality(profQualityDTO);
    }

    /**
     * 新增职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(@RequestBody ProfTitleDTO profTitleDTO) {
        return iAddDeleteTalentService.addProfTitle(profTitleDTO);
    }

    /**
     * 新增人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(@RequestBody TalentHonourDTO talentHonourDTO) {
        return iAddDeleteTalentService.addTalentHonour(talentHonourDTO);
    }

    /**
     * 删除学历
     * @param openId
     * @param educId
     * @return
     */
    @RequestMapping("deleteEducation")
    public ResultVO deleteEducation(@RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "educId") Long educId) {
        return iAddDeleteTalentService.deleteEducation(openId, educId);
    }

    /**
     * 删除职业资格
     * @param openId
     * @param pqId
     * @return
     */
    @RequestMapping("deleteProfQuality")
    public ResultVO deleteProfQuality(@RequestParam(value = "openId") String openId,
                                      @RequestParam(value = "pqId") Long pqId) {
        return iAddDeleteTalentService.deleteProfQuality(openId, pqId);
    }

    /**
     * 删除职称
     * @param openId
     * @param ptId
     * @return
     */
    @RequestMapping("deleteProfTitle")
    public ResultVO deleteProfTitle(@RequestParam(value = "openId") String openId,
                                    @RequestParam(value = "ptId") Long ptId) {
        return iAddDeleteTalentService.deleteProfTitle(openId, ptId);
    }

    /**
     * 删除人才荣誉
     * @param openId
     * @param thId
     * @return
     */
    @RequestMapping("deleteTalentHonour")
    public ResultVO deleteTalentHonour(@RequestParam(value = "openId") String openId,
                                       @RequestParam(value = "thId") Long thId) {
        return iAddDeleteTalentService.deleteTalentHonour(openId, thId);
    }
}
