package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfQualityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProfQualityMapper继承基类
 */
@Mapper
public interface ProfQualityMapper extends BaseMapper<ProfQualityPO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);

    /**
     * 根据认证ID更新职业资格表中的认证状态
     * @param certId
     * @param status
     * @return
     */
    int updateStatusByCertId(@Param("certId") Long certId, @Param("status") Byte status);
}