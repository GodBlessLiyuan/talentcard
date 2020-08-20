package com.talentcard.common.mapper;

import com.talentcard.common.bo.BankInfoBO;
import com.talentcard.common.pojo.BankPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * BankMapper继承基类
 */
@Mapper
public interface BankMapper extends BaseMapper<BankPO, Long> {
    /***
     * 根据talentId查找当前人才最近一次申请政策填写的银行卡信息
     * 用于申请接口中回填银行信息
     * @param talentId
     * @return
     */
    BankInfoBO findBankInfo(@Param("talentId") Long talentId);
}