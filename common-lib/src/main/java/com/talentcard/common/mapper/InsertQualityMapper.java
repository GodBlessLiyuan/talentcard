package com.talentcard.common.mapper;

import com.talentcard.common.pojo.InsertHonourPO;
import com.talentcard.common.pojo.InsertQualityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * InsertQualityMapper继承基类
 */
@Mapper
public interface InsertQualityMapper extends BaseMapper<InsertQualityPO, Long> {
    InsertQualityPO selectByInsertCertId(Long insertCertId);
}