package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EducationPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * EducationMapper继承基类
 */
@Mapper
public interface EducationMapper extends BaseMapper<EducationPO, Long> {
}