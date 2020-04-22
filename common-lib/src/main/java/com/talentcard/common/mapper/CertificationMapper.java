package com.talentcard.common.mapper;

import com.talentcard.common.bo.ApprovalBO;
import com.talentcard.common.bo.TalentCertStatusBO;
import com.talentcard.common.pojo.CertificationPO;
import com.talentcard.common.pojo.TalentPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CertificationMapper继承基类
 */
@Mapper
public interface CertificationMapper extends BaseMapper<CertificationPO, Long> {
    /**
     * insert返回主键
     * @param certificationPO
     * @return
     */
    Integer add(CertificationPO certificationPO);

    /**
     * 进行人才的审批状态查询
     * @param map
     * @return
     */
    List<TalentCertStatusBO> queryAllCert(Map<String, Object> map);

    /**
     * 根据用户姓名查询当前用的认证审批信息
     * @param tanlentId
     * @return
     */
    ApprovalBO queryAllMsg(Long tanlentId);

    /**
     * 根据talentId，更新认证表从一个状态更新到另一个状态
     * @param talentId
     * @param currentStatus
     * @param status
     * @return
     */
    int updateStatusById(@Param("talentId") Long talentId,
                         @Param("currentStatus") Byte currentStatus, @Param("status") Byte status);

    /**
     * 根据talentId，找到c表里status=1，正常使用的信息
     * @param talentId
     * @return
     */
    CertificationPO findCurrentCertification(@Param("talentId") Long talentId);
}