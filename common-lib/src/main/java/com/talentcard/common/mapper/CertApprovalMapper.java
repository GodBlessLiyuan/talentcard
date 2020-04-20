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

    /**
     * 根据certId查询 认证审批表type= 2 and result = 1
     * 找cert_approval表关于审批，且是通过的那条记录
     * @param certId
     * @return
     */
    CertApprovalPO findByCertId(Long certId);
}