package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.EditTalentRecordMapper;
import com.talentcard.common.pojo.EditTalentRecordPO;
import com.talentcard.web.service.IEditTalentRecordService;
import com.talentcard.web.service.IEditTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-09 09:12
 * @description
 */
@Service
public class EditTalentRecordServiceImpl implements IEditTalentRecordService {
    @Autowired
    EditTalentRecordMapper editTalentRecordMapper;

    @Override
    public Integer addRecord(HttpSession httpSession, Long talentId, Byte operationType, Byte operationContent, String beforeJson, String afterJson) {
        EditTalentRecordPO editTalentRecordPO = new EditTalentRecordPO();
        editTalentRecordPO.setOperationContent(operationContent);
        editTalentRecordPO.setOperationType(operationType);
        editTalentRecordPO.setCreateTime(new Date());
        editTalentRecordPO.setTalentId(talentId);
        editTalentRecordPO.setUserId((Long) httpSession.getAttribute("userId"));
        editTalentRecordPO.setComment("");
        editTalentRecordPO.setBeforeJsonRecord(beforeJson);
        editTalentRecordPO.setAfterJsonRecord(afterJson);
        Integer insertResult = editTalentRecordMapper.insertSelective(editTalentRecordPO);
        return insertResult;
    }
}
