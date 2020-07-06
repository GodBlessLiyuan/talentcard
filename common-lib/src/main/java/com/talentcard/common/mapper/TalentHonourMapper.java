package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.TalentHonourPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据certId查找对应th表信息
     * @param certId
     * @return
     */
    TalentHonourPO selectByCertId(@Param("certId") Long certId);

    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Long> queryNameByTalentId(Long talentId);

    /**
     * 根据certId查找对应th表信息
     * @param certId
     * @return
     */
    List<TalentHonourPO> findAllByCertId(@Param("certId") Long certId);

}