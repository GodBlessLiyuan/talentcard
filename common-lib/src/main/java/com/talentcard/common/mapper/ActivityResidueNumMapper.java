package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ActivityResidueNumPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ActivityResidueNumMapper继承基类
 */
@Mapper
public interface ActivityResidueNumMapper extends BaseMapper<ActivityResidueNumPO, Long> {
    ActivityResidueNumPO findOne(@Param("time") String time);
}