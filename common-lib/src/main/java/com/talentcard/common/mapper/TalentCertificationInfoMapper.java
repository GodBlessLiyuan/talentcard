package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentCertificationInfoPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TalentCertificationInfoMapper继承基类
 */
@Mapper
public interface TalentCertificationInfoMapper extends BaseMapper<TalentCertificationInfoPO, Long> {
    TalentCertificationInfoPO selectByTalentId(Long talentId);
}