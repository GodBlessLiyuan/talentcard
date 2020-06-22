package com.talentcard.common.mapper;

import com.talentcard.common.pojo.InsertCertificationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * InsertCertificationMapper继承基类
 */
@Mapper
public interface InsertCertificationMapper extends BaseMapper<InsertCertificationPO, Long> {
    Integer add(InsertCertificationPO insertCertificationPO);
}