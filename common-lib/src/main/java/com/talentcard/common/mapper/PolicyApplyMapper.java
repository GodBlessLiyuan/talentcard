package com.talentcard.common.mapper;

import com.talentcard.common.bo.ApplyNumCountBO;
import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.dto.ApplyNumCountDTO;
import com.talentcard.common.pojo.PolicyApplyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PolicyApplyMapper继承基类
 */
@Mapper
public interface PolicyApplyMapper extends BaseMapper<PolicyApplyPO, Long> {
    /**
     * 根据人才ID查询权益申请数据
     *
     * @param talentId
     * @return
     */
    List<PolicyApplyPO> queryByTalentId(Long talentId);

    /**
     * 查询详细信息
     *
     * @param paid
     * @return
     */
    PolicyApplyBO queryDetail(Long paid);

    /**
     * 根据人才ID和政策ID查询
     *
     * @param talentId
     * @param policyId
     * @return
     */
    List<PolicyApplyPO> queryByTidAndPidAndMonth(@Param("talentId") Long talentId, @Param("policyId") Long policyId, @Param("month") Integer month);

    /**
     * 计算相关状态的数量
     *
     * @param roleId
     * @return
     */
    Long countWaitApproval(@Param("roleId")Long roleId);

    /**
     * 计算具体责任单位相关状态的申请数量
     *
     * @param applyNumCountDTO
     * @return
     */
    ApplyNumCountBO applyNumCount(ApplyNumCountDTO applyNumCountDTO);
    /**
     * 根据人才id查询银行卡信息
     *
     * @param paId
     * @return
     */
    List<PoComplianceBO> queryBankByTalentId(@Param("talentId")Long paId,@Param("policyId")Long policyId);

    /**
     * 查询所有的请求信息
     * @return
     */
    List<PolicyApplyPO> selectAll();

    /**
     * 查询条件talentId，policyId，status
     * @param map
     * @return
     */
    List<PolicyApplyPO> selectByMap(Map map);
}