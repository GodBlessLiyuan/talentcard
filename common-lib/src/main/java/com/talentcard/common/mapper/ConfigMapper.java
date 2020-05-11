package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ConfigPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ConfigMapper继承基类
 */
@Mapper
public interface ConfigMapper extends BaseMapper<ConfigPO, String> {
}