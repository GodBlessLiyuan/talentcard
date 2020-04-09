package com.talentcard.common.mapper;

import com.talentcard.common.pojo.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleMapper继承基类
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {
}