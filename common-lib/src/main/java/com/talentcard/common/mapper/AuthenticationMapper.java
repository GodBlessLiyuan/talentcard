package com.talentcard.common.mapper;

import com.talentcard.common.pojo.Authentication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AuthenticationMapper继承基类
 */
@Mapper
@Repository
public interface AuthenticationMapper extends BaseMapper<Authentication, Long> {
}