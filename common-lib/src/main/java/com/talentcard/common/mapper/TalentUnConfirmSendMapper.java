package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentUnConfirmSendPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TalentUnConfirmSendMapper继承基类
 */
@Mapper
public interface TalentUnConfirmSendMapper extends BaseMapper<TalentUnConfirmSendPO, Long> {
    void batchInsert(List<TalentUnConfirmSendPO> originTalents);

    List<TalentUnConfirmSendPO> getUnSend(@Param("status") Byte status,@Param("sendIncr") Integer sendIncr);

    void updateStatusAndUpdateTime(TalentUnConfirmSendPO sendPO);
}