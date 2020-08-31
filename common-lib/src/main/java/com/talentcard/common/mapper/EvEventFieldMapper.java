package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventFieldPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * EvEventFieldMapper继承基类
 */
@Mapper
public interface EvEventFieldMapper extends BaseMapper<EvEventFieldPO, Long> {
    List<EvEventFieldPO> queryAll();
}