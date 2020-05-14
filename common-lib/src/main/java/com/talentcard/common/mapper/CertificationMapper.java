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
     *
     * @param certificationPO
     * @return
     */
    Integer add(CertificationPO certificationPO);

    /**
     * 进行人才的审批状态查询
     *
     * @param map
     * @return
     */
    List<TalentCertStatusBO> queryAllCert(Map<String, Object> map);

    /**
     * 根据用户姓名查询当前用的认证审批信息
     *
     * @param tanlentId
     * @return
     */
    ApprovalBO queryAllMsg(@Param("talentId") Long tanlentId, @Param("certId") Long certId);

    /**
     * 查看详情
     *
     * @param tanlentId
     * @return
     */
    ApprovalBO queryAllMsgLook(@Param("talentId") Long tanlentId, @Param("certId") Long certId);

    /**
     * 根据talentId，更新认证表从一个状态更新到另一个状态
     *
     * @param talentId
     * @param currentStatus
     * @param status
     * @return
     */
    int updateStatusById(@Param("talentId") Long talentId,
                         @Param("currentStatus") Byte currentStatus, @Param("status") Byte status);

    /**
     * 根据talentId，找到c表里status=1，正常使用的信息
     *
     * @param talentId
     * @return
     */
    CertificationPO findCurrentCertification(@Param("talentId") Long talentId);

    /**
     * 判断旧卡是基本卡还是高级卡，基本卡就改为失效9，高级卡改为失效10
     * 查询状态=9的数量，如果是0，说明这个是注册的基本卡换高级卡
     * 如果不为0，说明这个是高级卡换高级卡
     *
     * @param openId
     * @return
     */
    Integer ifOldCardIsBaseCard(@Param("openId") String openId);

    /**
     * 查询status=3的数据，查看是否已经拥有待审批数据了
     *
     * @param openId
     * @return
     */
    Integer ifWaitingApproval(@Param("openId") String openId);

    /**
     * 查询当前待审批记录的数量
     * 后台左侧栏位的小红点
     * c表status=3：待审批
     * type=2：初级卡换高级卡
     *
     * @return
     */
    Integer findWaitApprovalNum();

    /**
     * 根据认证ID更新C表中的认证状态
     *
     * @param certId
     * @param status
     * @return
     */
    int updateStatusByCertId(@Param("certId") Long certId, @Param("status") Byte status);

    /**
     * 发高级卡之前判断数之前据是否为脏数据
     *
     * @return
     */
    Integer checkIfDirty(@Param("talentId") Long talentId,
                         @Param("cStatus") Byte cStatus, @Param("ucStatus") Byte ucStatus);
}