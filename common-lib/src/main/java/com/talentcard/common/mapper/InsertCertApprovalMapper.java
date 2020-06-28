package com.talentcard.common.mapper;

import com.talentcard.common.bo.InsertCertApprovalBO;
import com.talentcard.common.pojo.InsertCertApprovalPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InsertCertApprovalMapper继承基类
 */
@Mapper
public interface InsertCertApprovalMapper extends BaseMapper<InsertCertApprovalPO, Long> {
    List<InsertCertApprovalBO> findRecord(@Param("talentId") Long talentId);
}