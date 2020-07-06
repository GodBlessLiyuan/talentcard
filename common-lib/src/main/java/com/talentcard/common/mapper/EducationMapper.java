package com.talentcard.common.mapper;

import com.talentcard.common.pojo.EducationPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EducationMapper继承基类
 */
@Mapper
public interface EducationMapper extends BaseMapper<EducationPO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);

    /**
     * 根据认证ID更新学历表中的认证状态
     * @param certId
     * @param status
     * @return
     */
    int updateStatusByCertId(@Param("certId") Long certId, @Param("status") Byte status);

    /**
     * 根据certId查找对应教育表信息
     * @param certId
     * @return
     */
    EducationPO selectByCertId(@Param("certId") Long certId);

    /**
     * 根据certId查找所有对应教育表信息
     * @param certId
     * @return
     */
    List<EducationPO> findAllByCertId(@Param("certId") Long certId);
}