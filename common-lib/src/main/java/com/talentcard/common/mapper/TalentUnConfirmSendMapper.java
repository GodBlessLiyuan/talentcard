package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentUnConfirmSendPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TalentUnConfirmSendMapper继承基类
 */
@Mapper
public interface TalentUnConfirmSendMapper extends BaseMapper<TalentUnConfirmSendPO, Long> {
}