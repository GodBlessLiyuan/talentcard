package com.talentcard.common.mapper;

import com.talentcard.common.pojo.TalentHonourPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TalentHonourMapper继承基类
 */
@Mapper
public interface TalentHonourMapper extends BaseMapper<TalentHonourPO, Long> {
    /**
     * 根据认证ID更新人才荣誉表中的认证状态
     * @param certId
     * @param status
     * @return
     */
    int updateStatusByCertId(@Param("certId") Long certId, @Param("status") Byte status);

}