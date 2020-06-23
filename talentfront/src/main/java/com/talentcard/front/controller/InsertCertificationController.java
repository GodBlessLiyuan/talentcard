package com.talentcard.front.controller;

import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IInsertCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-22 09:28
 * @description
 */
@RequestMapping("insertCertification")
@RestController
public class InsertCertificationController {
    @Autowired
    IInsertCertificationService iInsertCertificationService;

    /**
     * 学历
     * @param educationDTO
     * @return
     */
    @RequestMapping("addEducation")
    public ResultVO addEducation(@RequestBody EducationDTO educationDTO) {
        return iInsertCertificationService.addEducation(educationDTO);
    }

    /**
     * 职业资格
     * @param profQualityDTO
     * @return
     */
    @RequestMapping("addProfQuality")
    public ResultVO addProfQuality(@RequestBody ProfQualityDTO profQualityDTO) {
        return iInsertCertificationService.addProfQuality(profQualityDTO);
    }

    /**
     * 职称
     * @param profTitleDTO
     * @return
     */
    @RequestMapping("addProfTitle")
    public ResultVO addProfTitle(@RequestBody ProfTitleDTO profTitleDTO) {
        return iInsertCertificationService.addProfTitle(profTitleDTO);
    }

    /**
     * 人才荣誉
     * @param talentHonourDTO
     * @return
     */
    @RequestMapping("addTalentHonour")
    public ResultVO addTalentHonour(@RequestBody TalentHonourDTO talentHonourDTO) {
        return iInsertCertificationService.addTalentHonour(talentHonourDTO);
    }

    /**
     * 删除
     * @param insertCertId
     * @return
     */
    @RequestMapping("delete")
    public ResultVO delete(@RequestParam(value = "insertCertId") Long insertCertId) {
        return iInsertCertificationService.delete(insertCertId);
    }

}
