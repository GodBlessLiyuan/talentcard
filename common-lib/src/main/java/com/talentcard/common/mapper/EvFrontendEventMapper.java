package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvFrontendEventPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * EvFrontendEventMapper继承基类
 */
@Mapper
public interface EvFrontendEventMapper extends BaseMapper<EvFrontendEventPO, Long> {
}