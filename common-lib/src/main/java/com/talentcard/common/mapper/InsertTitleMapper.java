package com.talentcard.common.mapper;

import com.talentcard.common.pojo.InsertEducationPO;
import com.talentcard.common.pojo.InsertTitlePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * InsertTitleMapper继承基类
 */
@Mapper
public interface InsertTitleMapper extends BaseMapper<InsertTitlePO, Long> {
    InsertTitlePO selectByInsertCertId(Long insertCertId);
}