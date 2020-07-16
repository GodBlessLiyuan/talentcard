package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseMonthPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * FarmhouseMonthMapper继承基类
 */
@Mapper
public interface FarmhouseMonthMapper extends BaseMapper<FarmhouseMonthPO, Long> {
}