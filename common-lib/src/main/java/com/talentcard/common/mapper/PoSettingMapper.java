package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoSettingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * PoSettingMapper继承基类
 */
@Mapper
public interface PoSettingMapper extends BaseMapper<PoSettingPO, Long> {
    /**
     * 根据policyId删除相关所有信息
     * @param policyId
     */
    Integer deleteByPolicyId(@Param("policyId")Long policyId);
}