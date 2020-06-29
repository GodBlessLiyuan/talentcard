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
     * 学历
     *
     * @param educationDTO
     * @return
     */
    @RequestMapping("addEducation")
    public ResultVO addEducation(@RequestBody EducationDTO educationDTO) {
        return iAddDeleteTalentService.addEducation(educationDTO);
    }

    /**
     * 职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(@RequestBody ProfQualityDTO profQualityDTO) {
        return iAddDeleteTalentService.addProfQuality(profQualityDTO);
    }

    /**
     * 职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(@RequestBody ProfTitleDTO profTitleDTO) {
        return iAddDeleteTalentService.addProfTitle(profTitleDTO);
    }

    /**
     * 人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(@RequestBody TalentHonourDTO talentHonourDTO) {
        return iAddDeleteTalentService.addTalentHonour(talentHonourDTO);
    }

}
