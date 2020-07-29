package com.talentcard.common.mapper;

import com.talentcard.common.bo.EditTalentRecordBO;
import com.talentcard.common.pojo.EditTalentRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * EditTalentRecordMapper继承基类
 */
@Mapper
public interface EditTalentRecordMapper extends BaseMapper<EditTalentRecordPO, Long> {
    List<EditTalentRecordBO> queryByTalentId(String talentId);
}
