package com.talentcard.common.mapper;

import com.talentcard.common.pojo.ProfQualityPO;
import com.talentcard.common.pojo.ProfTitlePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProfTitleMapper继承基类
 */
@Mapper
public interface ProfTitleMapper extends BaseMapper<ProfTitlePO, Long> {
    /**
     * 根据人才ID查询
     *
     * @param talentId
     * @return
     */
    List<Integer> queryNameByTalentId(Long talentId);


    /**
     * 根据认证ID更新职称表中的认证状态
     * @param certId
     * @param status
     * @return
     */
    int updateStatusByCertId(@Param("certId") Long certId, @Param("status") Byte status);

    /**
     * 根据certId查找对应pt表信息
     * @param certId
     * @return
     */
    ProfTitlePO selectByCertId(@Param("certId") Long certId);
}