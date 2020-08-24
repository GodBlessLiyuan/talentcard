package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.pojo.PolicyApprovalPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PolicyApprovalMapper继承基类
 */
@Mapper
public interface PolicyApprovalMapper extends BaseMapper<PolicyApprovalPO, Long> {
    Integer add(PolicyApprovalPO policyApprovalPO);

    /**
     * 获取当前所有的申请数据
     * @return
     */
    List<PolicyApprovalPO> selectAll();
}