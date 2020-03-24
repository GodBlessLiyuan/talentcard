package com.talentcard.common.mapper;

import com.talentcard.common.pojo.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AuthorityMapper继承基类
 */
@Mapper
@Repository
public interface AuthorityMapper extends BaseMapper<Authority, Long> {
}