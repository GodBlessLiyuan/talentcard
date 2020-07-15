package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseMouthPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * FarmhouseMouthMapper继承基类
 */
@Mapper
public interface FarmhouseMouthMapper extends BaseMapper<FarmhouseMouthPO, Long> {
}