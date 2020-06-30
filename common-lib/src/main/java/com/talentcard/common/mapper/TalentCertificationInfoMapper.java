package com.talentcard.common.mapper;

import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.bo.TalentCertificationBO;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TalentCertificationInfoMapper继承基类
 */
@Mapper
public interface TalentCertificationInfoMapper extends BaseMapper<TalentCertificationInfoPO, Long> {
    /**
     * 根据talentId拿PO
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
}