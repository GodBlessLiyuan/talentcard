package com.talentcard.common.mapper;

import com.talentcard.common.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * MemberMapper继承基类
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member, Long> {
}