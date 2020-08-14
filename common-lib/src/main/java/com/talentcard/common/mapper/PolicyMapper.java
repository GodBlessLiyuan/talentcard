package com.talentcard.common.mapper;

import com.talentcard.common.bo.HavingApprovePolicyBO;
import com.talentcard.common.pojo.BankPO;
import com.talentcard.common.pojo.PolicyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PolicyMapper继承基类
 */
@Mapper
public interface PolicyMapper extends BaseMapper<PolicyPO, Long> {

    /**
     * 根据dr值查询
     *
     * @param dr
     * @return
     */
    List<PolicyPO> queryByDr(Byte dr);

    /**
     * 根据num查询
     *
     * @param num
     * @return
     */
    PolicyPO queryByNum(String num);

    /**
     * 根據openId獲取銀行卡信息
     * @param openId
     * @return
     */
    List<BankPO> queryBankCardInfo(String openId);

    /**
     * 添加，返回主键
     * @param policyPO
     * @return
     */
    Integer add(PolicyPO policyPO);

    List<HavingApprovePolicyBO> findHavingApprovePolicy(@Param("talentId") Long talentId);
}