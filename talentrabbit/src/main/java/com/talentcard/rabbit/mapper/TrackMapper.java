package com.talentcard.rabbit.mapper;

import com.talentcard.rabbit.pojo.TrackPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TrackMapper继承基类
 */
@Mapper
public interface TrackMapper extends BaseMapper<TrackPO, Long> {
}