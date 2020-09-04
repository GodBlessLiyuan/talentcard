package com.talentcard.common.mapper;

import com.talentcard.common.pojo.SocialSecurityPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * SocialSecurityMapper继承基类
 */
@Mapper
public interface SocialSecurityMapper extends BaseMapper<SocialSecurityPO, Long> {
}