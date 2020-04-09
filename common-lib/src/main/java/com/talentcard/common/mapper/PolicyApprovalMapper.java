package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyApprovalPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PolicyApprovalMapper继承基类
 */
@Mapper
public interface PolicyApprovalMapper extends BaseMapper<PolicyApprovalPO, Long> {
}