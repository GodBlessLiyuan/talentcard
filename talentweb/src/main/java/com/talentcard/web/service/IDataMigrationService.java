package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-11 15:52
 * @description
 */
public interface IDataMigrationService {
    /**
     * 认证审批
     * @return
     */
    ResultVO certExamineRecord();

    /**
     * 认证人才
     * @return
     */
    ResultVO certTalent();
}
