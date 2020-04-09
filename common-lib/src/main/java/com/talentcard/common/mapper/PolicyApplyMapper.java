package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PolicyApplyPO;
import org.apache.ibatis.annotations.Mapper;

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
}