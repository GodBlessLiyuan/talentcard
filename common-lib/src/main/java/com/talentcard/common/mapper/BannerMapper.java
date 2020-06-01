package com.talentcard.common.mapper;

import com.talentcard.common.pojo.BannerPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * BannerMapper继承基类
 */
@Mapper
public interface BannerMapper extends BaseMapper<BannerPO, Long> {
}