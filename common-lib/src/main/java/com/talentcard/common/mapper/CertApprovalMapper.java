package com.talentcard.common.mapper;

import com.talentcard.common.pojo.CertApprovalPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CertApprovalMapper继承基类
 */
@Mapper
public interface CertApprovalMapper extends BaseMapper<CertApprovalPO, Long> {

    /**
     * 根据人才talentId查询 认证审批表
     * @param talentId
     * @return
     */
    List<CertApprovalPO> queryApprovalById(Long talentId);


    int queryWaitApprovalNum();
}