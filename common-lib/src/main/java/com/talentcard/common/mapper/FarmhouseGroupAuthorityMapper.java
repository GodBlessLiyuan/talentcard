package com.talentcard.common.mapper;

import com.talentcard.common.pojo.FarmhouseGroupAuthorityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FarmhouseGroupAuthorityMapper继承基类
 */
@Mapper
public interface FarmhouseGroupAuthorityMapper extends BaseMapper<FarmhouseGroupAuthorityPO, Long> {
    List<Long> findByCode(@Param("code") String code);
}