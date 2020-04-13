package com.talentcard.common.mapper;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.pojo.PolicyApplyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<PolicyApplyPO> queryByTidAndPid(@Param("talentId") Long talentId, @Param("policyId") Long policyId);
}