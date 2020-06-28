package com.talentcard.web.controller;

import com.talentcard.common.dto.BasicInfoDTO;
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


}
