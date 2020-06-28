package com.talentcard.web.controller;

import com.talentcard.common.dto.*;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IEditTalentService;
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
@RequestMapping("editTalent")
@RestController
public class EditTalentController {
    IEditTalentService iEditTalentService;

    @RequestMapping("editBasicInfo")
    public ResultVO editBasicInfo(@RequestBody BasicInfoDTO basicInfoDTO) {
        return iEditTalentService.editBasicInfo(basicInfoDTO);
    }

    /**
     * 学历
     *
     * @param educationDTO
     * @return
     */
    @RequestMapping("editEducation")
    public ResultVO editEducation(@RequestBody EducationDTO educationDTO) {
        return iEditTalentService.editEducation(educationDTO);
    }

    /**
     * 职业资格
     *
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("editProfQuality")
    public ResultVO editProfQuality(@RequestBody ProfQualityDTO profQualityDTO) {
        return iEditTalentService.editProfQuality(profQualityDTO);
    }

    /**
     * 职称
     *
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("editProfTitle")
    public ResultVO editProfTitle(@RequestBody ProfTitleDTO profTitleDTO) {
        return iEditTalentService.editProfTitle(profTitleDTO);
    }

    /**
     * 人才荣誉
     *
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("editTalentHonour")
    public ResultVO editTalentHonour(@RequestBody TalentHonourDTO talentHonourDTO) {
        return iEditTalentService.editTalentHonour(talentHonourDTO);
    }

    /**
     * 人才类别
     *
     * @param openId
     * @param talentCategory
     * @return
     */
    @RequestMapping("editTalentCategory")
    public ResultVO editTalentCategory(@RequestParam(value = "openId") String openId,
                                     @RequestParam(value = "talentCategory") String talentCategory) {
        return iEditTalentService.editTalentCategory(openId, talentCategory);
    }
}
