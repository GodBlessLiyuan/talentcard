package com.talentcard.web.service;

import javax.servlet.http.HttpSession;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-09 09:12
 * @description
 */
public interface IEditTalentRecordService {
    Integer addRecord(HttpSession httpSession, Long talentId, Byte operationType,
                      Byte operationContent, String beforeJson,
                      String afterJson, String opinion);
}
