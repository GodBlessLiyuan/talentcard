package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CertificationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * CertificationMapper继承基类
 */
@Mapper
public interface CertificationMapper extends BaseMapper<CertificationPO, Long> {
    Integer add(CertificationPO certificationPO);
}