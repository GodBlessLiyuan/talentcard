package com.talentcard.common.mapper;

import com.talentcard.common.pojo.AuthorityPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AuthorityMapper继承基类
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<AuthorityPO, Long> {
    java.lang.String queryByAuthorityId(long authorityId);
}