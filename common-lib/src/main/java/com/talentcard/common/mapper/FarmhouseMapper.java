package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhousePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * FarmhouseMapper继承基类
 */
@Mapper
public interface FarmhouseMapper extends BaseMapper<FarmhousePO, Long> {
}