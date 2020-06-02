package com.talentcard.common.mapper;

import com.talentcard.common.pojo.BannerPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BannerMapper继承基类
 */
@Mapper
public interface BannerMapper extends BaseMapper<BannerPO, Long> {
    List<BannerPO> bannerQuery();

    /**
     * 设置状态
     *
     * @param bid
     * @param status
     */
    void status(@Param("bannerId") Long bid, @Param("status") Byte status);
}