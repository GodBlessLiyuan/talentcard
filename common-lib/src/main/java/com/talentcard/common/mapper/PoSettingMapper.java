package com.talentcard.common.mapper;

import com.talentcard.common.pojo.PoSettingPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有政策相关的条件
     * @return
     */
    List<PoSettingPO> selectByPolicyId(Long policyId);

    /**
     * 查询获取对应的政策信息
     * @param map
     * @return
     */
    List<Long> selectByType(Map map);
}