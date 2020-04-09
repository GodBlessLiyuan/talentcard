package com.talentcard.common.mapper;

import com.talentcard.common.pojo.RoleAuthorityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RoleAuthorityMapper继承基类
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityPO, Long> {
}