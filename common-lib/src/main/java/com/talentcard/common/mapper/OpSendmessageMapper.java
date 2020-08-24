package com.talentcard.common.mapper;

import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.pojo.OpSendmessagePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * OpSendmessageMapper继承基类
 */
@Mapper
public interface OpSendmessageMapper extends BaseMapper<OpSendmessagePO, Long> {
    /**
     * 根据政策id查询推送记录
     *
     * @param pId
     * @return
     */
    List<OpSendmessagePO> pushRecordQuery(Long pId);
}