package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TripGroupAuthorityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TripGroupAuthorityMapper继承基类
 */
@Mapper
public interface TripGroupAuthorityMapper extends BaseMapper<TripGroupAuthorityPO, Long> {
    List<Long> findByCode(@Param("code") String code);
}