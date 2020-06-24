package com.talentcard.common.mapper;

import com.talentcard.common.pojo.InsertEducationPO;
import com.talentcard.common.pojo.InsertHonourPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * InsertHonourMapper继承基类
 */
@Mapper
public interface InsertHonourMapper extends BaseMapper<InsertHonourPO, Long> {
    InsertHonourPO selectByInsertCertId(Long insertCertId);
}