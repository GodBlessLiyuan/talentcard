package com.talentcard.common.mapper;

import com.talentcard.common.pojo.MemberPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * MemberMapper继承基类
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<MemberPO, Long> {
}