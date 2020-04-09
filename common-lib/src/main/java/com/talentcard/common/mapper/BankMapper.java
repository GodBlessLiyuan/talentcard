package com.talentcard.common.mapper;

import com.talentcard.common.pojo.BankPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * BankMapper继承基类
 */
@Mapper
public interface BankMapper extends BaseMapper<BankPO, Long> {
}