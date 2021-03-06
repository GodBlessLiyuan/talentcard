package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EvEventQueryPO;
import com.talentcard.common.pojo.EvFrontendEventApprovalPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EvFrontendEventApprovalMapper继承基类
 */
@Mapper
public interface EvFrontendEventApprovalMapper extends BaseMapper<EvFrontendEventApprovalPO, Long> {
    EvFrontendEventApprovalPO queryByFeid(@Param("feid")Long eid);
    List<EvFrontendEventApprovalPO> queryApprovalByFeid(@Param("feid")Long eid);
}