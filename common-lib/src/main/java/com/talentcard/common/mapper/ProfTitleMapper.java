package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfTitlePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ProfTitleMapper继承基类
 */
@Mapper
public interface ProfTitleMapper extends BaseMapper<ProfTitlePO, Long> {
}