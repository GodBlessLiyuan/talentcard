package com.talentcard.web.controller;

import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IDataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-11 15:47
 * @description
 */
@RequestMapping("dataMigration")
@RestController
public class DataMigrationController {
    @Autowired
    IDataMigrationService iDataMigrationService;

    /**
     * 认证审批表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certExamineRecord")
    public ResultVO certExamineRecord() {
        return iDataMigrationService.certExamineRecord();
    }

    /**
     * 认证人才表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certTalent")
    public ResultVO certTalent() {
        return iDataMigrationService.certTalent();
    }
}
