package com.talentcard.common.mapper;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.pojo.CertificationPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * CertificationMapper继承基类
 */
@Mapper
public interface CertificationMapper extends BaseMapper<CertificationPO, Long> {
    Integer add(CertificationPO certificationPO);

    /**
     * 进行人才的审批状态查询
     * @param map
     * @return
     */
    List<TalentCertStatusBO> queryAllCert(Map<String, Object> map);

    /**
     * 根据用户姓名查询当前用的认真审批信息
     * @param name
     * @return
     */
    ApprovalBO queryAllMsg(String name);
}