package com.talentcard.common.mapper;

import com.talentcard.common.bo.ExportCertInfoBO;
import com.talentcard.common.bo.TalentCertificationBO;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * TalentCertificationInfoMapper继承基类
 */
@Mapper
public interface TalentCertificationInfoMapper extends BaseMapper<TalentCertificationInfoPO, Long> {
    /**
     * 根据talentId拿PO
     *
     * @param talentId
     * @return
     */
    TalentCertificationInfoPO selectByTalentId(Long talentId);

    /**
     * 认证人才查询
     *
     * @param map
     * @return
     */
    List<TalentCertificationBO> queryCertTalent(Map<String, Object> map);

    /**
     * 查询表里面所有的数据
     *
     * @return
     */
    List<TalentCertificationInfoPO> selectAll();
    /**
     * 导出人才认证信息
     *
     * @return
     */
    List<ExportCertInfoBO> exportCertInfo();
}